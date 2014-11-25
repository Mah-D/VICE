package vpc.stack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import vpc.Interpreter;
import vpc.Interpreter.BaseMonitor;
import vpc.core.base.PrimRaw;
import vpc.core.csr.CSRType;
import vpc.core.decl.Method;
import vpc.core.types.Type;
import vpc.simu.IntptrScheduler;
import vpc.tir.TIRRep;
import vpc.tir.TIRUtil;
import vpc.tir.expr.Operator;
import cck.parser.SourcePoint;
import cck.text.StringUtil;
import cck.text.Terminal;

class CallTreeNode {
	Method method;
	CallTreeNode sibling;
	CallTreeNode children;
	int count;
	long accumulated;
	long startns;
}

class MethodStats {
	Method method;
	int invokeCount;
	long accounted;
}

public class StackSizeMonitor extends BaseMonitor {

	final int MAX_CALL_DEPTH = 40960;
	private final CallTreeNode[] stack = new CallTreeNode[MAX_CALL_DEPTH];
	float threshold = Float.class.cast(Interpreter.options.getOption("profile-threshold").getValue());
	private int position, currentStack, maxStack;
	private InterruptGraph m_interruptGraph;
	class methodStackItem {
		int stackCounter;
		Method m;

		public methodStackItem(int s, Method mm) {
			stackCounter = s;
			m = mm;
		};
	};

	public Stack<methodStackItem> stackMethodStack = new Stack<methodStackItem>();

	public StackSizeMonitor(Method main, InterruptGraph g) {
		stack[0] = new CallTreeNode();
		maxStack = currentStack = getMethodStack(main);
		m_interruptGraph = g;
		g.updateCurrentSize(maxStack, IntptrScheduler.Instance.backupAllRegs("maximalPoint"));
	}

	protected int getTypeSize(Type t) {
		int counter = 0;
		// no stack space for void return value
		if (!(t instanceof CSRType.Void)) {
			if (t instanceof PrimRaw.IType) {
				PrimRaw.IType p = (PrimRaw.IType) t;
				counter = (p.width + 7) / 8; // round up to byte
			} else {
				// integer and object reference have the same size
				counter = 4;
			}
		}
		return counter;
	}
	
	protected int getMethodStack(Method m) {
		int stackCounter = 0;
		stackCounter += 4; // stack space for the return address
		stackCounter += getTypeSize(m.getReturnType().getType());

		// count all local variables
		TIRRep body = TIRUtil.getRep(m);
		for (Method.Temporary<Type> t : body.getTemps()) {
			stackCounter += getTypeSize(t.getType());
		}
		return stackCounter;
	}

	public void stepIntoInterrupt() {
		currentStack++;
		if (currentStack > maxStack) {
			maxStack = currentStack;
		}
	}

	public void stepOutofInterrupt() {
		currentStack--;
	}

	public void fireBeforeCall(SourcePoint callpt, Method method) {

//		System.out.println(" stack position " + position);
//		System.out.println(" call function  " + method.getFullName());

		int methodStack = getMethodStack(method);
		stackMethodStack.push(new methodStackItem(methodStack, method));
		currentStack += methodStack;
		m_interruptGraph.updateCurrentSize(currentStack, IntptrScheduler.Instance.backupAllRegs("maximalPoint"));
		if (currentStack > maxStack)
			maxStack = currentStack;

		CallTreeNode parent = stack[position];
		CallTreeNode thisNode = parent.children;
		while (thisNode != null) {
			if (thisNode.method == method)
				break;
			thisNode = thisNode.sibling;
		}
		if (thisNode == null) {
			// didnt find a call tree node for this one, create and insert
			thisNode = new CallTreeNode();
			thisNode.method = method;
			thisNode.sibling = parent.children;
			parent.children = thisNode;
		}
		thisNode.startns = System.nanoTime();
		stack[++position] = thisNode;
	}

	public void fireAfterReturn(SourcePoint callpt) {
		// dump the stack after function return
		currentStack -= stackMethodStack.pop().stackCounter;

		// record the complete invocation time for this node
		long endns = System.nanoTime();
		CallTreeNode thisNode = stack[position--];
		thisNode.count++;
		thisNode.accumulated += endns - thisNode.startns;
	}
	public int getCurIntrStack(Method intr) {
		return getCurrentStack() + getMethodStack(intr);
	}
	public int getCurrentStack() {
		return currentStack;
	}

	public int getCurrentMaxStack() {
		return maxStack;
	}

	public List<methodStackItem> getCurrentStackTrace() {
		return stackMethodStack;
	}

	public void fireBeforeApply(SourcePoint srcpt, Operator operator) {
	}

	public void report() {
		long total = 0;
		CallTreeNode root = stack[0];
		CallTreeNode child = root.children;
		while (child != null) {
			total += child.accumulated;
			child = child.sibling;
		}

		if (Integer.class.cast(Interpreter.options.getOption("profile-tree")
				.getValue()) > 0) {
			reportSubTree(root, 0, total);
		}

		if (Boolean.class.cast(Interpreter.options.getOption(
				"profile-invoke-count").getValue())
				|| Boolean.class.cast(Interpreter.options.getOption(
						"profile-method-time").getValue())) {
			HashMap<Method, MethodStats> methodStats = collectMethodStats();
			if (Boolean.class.cast(Interpreter.options.getOption(
					"profile-invoke-count").getValue()))
				reportMethodStats(total, new InvokeCountComparator(),
						methodStats);
			if (Boolean.class.cast(Interpreter.options.getOption(
					"profile-method-time").getValue()))
				reportMethodStats(total, new AccountedComparator(), methodStats);
		}
	}

	private void reportSubTree(CallTreeNode node, int indent, double pval) {
		float total = asPercent(node.accumulated, pval);
		float inside = node.accumulated;
		CallTreeNode child = node.children;
		while (child != null) {
			inside -= child.accumulated;
			child = child.sibling;
		}
		inside = asPercent(inside, pval);
		if (node.method != null) {
			if (threshold > 0 && total < threshold)
				return;
			Terminal.print(StringUtil.space(indent * 4));
			Terminal.printGreen(StringUtil.leftJustify(node.method
					.getFullName(), 40));
		} else {
			Terminal.print(StringUtil.space(indent * 4));
			Terminal.printGreen("root");
		}
		Terminal.print(" x ");
		Terminal.printCyan(String.valueOf(node.count));
		Terminal.print(" = ");
		Terminal.printCyan(StringUtil.toFixedFloat(
				node.accumulated / 1000000000.0f, 6));
		Terminal.print(" s (");
		Terminal.print(StringUtil.toFixedFloat(inside, 5));
		Terminal.print(" / ");
		Terminal.print(StringUtil.toFixedFloat(total, 5));
		Terminal.print(" %)");
		Terminal.nextln();
		if (indent < Integer.class.cast(Interpreter.options.getOption(
				"profile-tree").getValue())) {
			child = node.children;
			while (child != null) {
				reportSubTree(child, indent + 1, pval);
				child = child.sibling;
			}
		}
	}

	private void reportMethodStats(long total,
			Comparator<MethodStats> comparator,
			HashMap<Method, MethodStats> methodStats) {
		final ArrayList<MethodStats> list = new ArrayList<MethodStats>(
				methodStats.values());
		Collections.sort(list, comparator);
		int cntr = 1;
		long top = 0;
		for (MethodStats stats : list) {
			top += stats.accounted;
			Terminal.print(StringUtil.justify(false, cntr, 5));
			Terminal.print(" (");
			Terminal.printCyan(StringUtil
					.toFixedFloat(asPercent(top, total), 5));
			Terminal.print(" %) ");
			Terminal.printGreen(StringUtil.leftJustify(stats.method
					.getFullName(), 40));
			Terminal.print(" x ");
			Terminal.printCyan(String.valueOf(stats.invokeCount));
			Terminal.print(" = ");
			Terminal.printCyan(StringUtil.toFixedFloat(
					stats.accounted / 1000000000.0f, 6));
			Terminal.print(" s (");
			Terminal.print(StringUtil.toFixedFloat(asPercent(stats.accounted,
					total), 5));
			Terminal.print(" %)");
			Terminal.nextln();
			cntr++;
		}
	}

	private HashMap<Method, MethodStats> collectMethodStats() {
		HashMap<Method, MethodStats> methodStats = new HashMap<Method, MethodStats>();
		collectMethodStats(stack[0], methodStats);
		return methodStats;
	}

	private static class InvokeCountComparator implements
			Comparator<MethodStats> {
		public int compare(MethodStats a, MethodStats b) {
			if (a.invokeCount < b.invokeCount)
				return 1;
			if (a.invokeCount > b.invokeCount)
				return -1;
			return 0;
		}
	}

	private static class AccountedComparator implements Comparator<MethodStats> {
		public int compare(MethodStats a, MethodStats b) {
			if (a.accounted < b.accounted)
				return 1;
			if (a.accounted > b.accounted)
				return -1;
			return 0;
		}
	}

	private void collectMethodStats(CallTreeNode node,
			HashMap<Method, MethodStats> statMap) {
		final Method method = node.method;
		if (method != null) {
			MethodStats stats = statMap.get(method);
			if (stats == null) {
				stats = new MethodStats();
				stats.method = method;
				statMap.put(method, stats);
			}
			long inside = node.accumulated;
			CallTreeNode child = node.children;
			while (child != null) {
				inside -= child.accumulated;
				child = child.sibling;
			}
			stats.accounted += inside;
			stats.invokeCount += node.count;
		}

		CallTreeNode child = node.children;
		while (child != null) {
			collectMethodStats(child, statMap);
			child = child.sibling;
		}
	}

	private float asPercent(double nval, double pval) {
		return (float) (100 * nval / pval);
	}
}
