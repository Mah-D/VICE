package vpc.dart.symb.solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;

import vpc.core.base.PrimInt32;
import vpc.dart.symb.exp.SymbExpBase;
import vpc.dart.symb.exp.SymbExpConst;
import vpc.dart.symb.exp.SymbExpOp;
import vpc.dart.symb.exp.SymbExpVariable;
import vpc.dart.symb.operator.compare.*;

public class SimpleSymbolicSolver extends SymbolicSolver {
	
	@Override
	protected boolean solveInternal(SymbExpBase[] constraints) {
		TreeMap<Integer, List<SymbExpOp>> groupExp = new TreeMap<Integer, List<SymbExpOp>>(); 
		for(int i = 0; i < constraints.length; i++) {
			SymbExpVariable var = (SymbExpVariable)constraints[i].findSubExp(SymbExpVariable.class);
			if(var != null) {
				if(!groupExp.containsKey(var.getId()))
					groupExp.put(var.getId(), new ArrayList<SymbExpOp>());
				if(constraints[i] instanceof SymbExpOp
						&& SymbExpOp.class.cast(constraints[i]).operator instanceof SymbCompBase) {
					groupExp.get(var.getId()).add(SymbExpOp.class.cast(constraints[i]));
				} else
					return false;
			}
		}
		for(Entry<Integer, List<SymbExpOp>> cond : groupExp.entrySet()) {
			if(!solveIntRange(cond.getKey(), 
					cond.getValue().toArray(new SymbExpOp[cond.getValue().size()])))
				return false;
		}
		return true;
	}
	
	private boolean solveIntRange(int index, SymbExpOp[] cond) {
		int low = Integer.MIN_VALUE, up = Integer.MAX_VALUE;
		ArrayList<Integer> list = new ArrayList<Integer>(); 
		for(SymbExpOp comp : cond) {
			int val = 0;
			boolean order = true;
			if(comp.operands[0] instanceof SymbExpConst) {
				val = PrimInt32.fromValue(SymbExpConst.class.cast(comp.operands[0]).getValue());
				order = false;
				if(comp.operands[1] instanceof SymbExpConst) {
					low = up = PrimInt32.fromValue(SymbExpConst.class.cast(comp.operands[1]).getValue());
				}
			}
			else if(comp.operands[1] instanceof SymbExpConst)
				val = PrimInt32.fromValue(SymbExpConst.class.cast(comp.operands[1]).getValue());
			else
				return false;
			SymbCompBase compOp = SymbCompBase.class.cast(comp.operator);
			int state = 0;
			if(compOp instanceof SymbCompEqual) {
				state = 1;
			} else if(compOp instanceof SymbCompGreater) {
				if(order)
					state = 2;
				else
					state = 3;
			} else if(compOp instanceof SymbCompGreaterEqual) {
				if(order)
					state = 4;
				else
					state = 5;
			} else if(compOp instanceof SymbCompLess) {
				if(order)
					state = 3;
				else
					state = 2;
			} else if(compOp instanceof SymbCompLessEqual) {
				if(order)
					state = 5;
				else
					state = 4;
			} else if (compOp instanceof SymbCompNotEqual){
				state = 6;
			}
			switch(state) {
			case 0: // no case
				return false;
			case 1: // equal
				if(val < low || val > up)
					return false;
				low = up = val;
				break;
			case 2: // greater
				if(val >= up)
					return false;
				low = val + 1;
				break;
			case 3: // less
				if(val <= low)
					return false;
				up = val - 1;
				break;
			case 4: // greater equal
				if(val > up)
					return false;
				low = val;
				break;
			case 5: // less equal
				if(val < low)
					return false;
				up = val;
				break;
			case 6:
				if(val >= low && val <= up)
					list.add(val);
			}
		}
		
		Collections.sort(list);
		for(int excep : list) {
			if(excep > low) {
				putResult(index, PrimInt32.toValue(low));
				return true;
			} else if(excep == low) {
				if(++low > up)
					return false;
			}
		}
		if(low <= up) {
			putResult(index, PrimInt32.toValue(low));
			return true;
		}
		return false;
	}
}
