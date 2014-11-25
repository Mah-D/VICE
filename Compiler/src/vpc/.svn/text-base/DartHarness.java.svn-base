package vpc;

import java.util.Arrays;

import cck.util.Util;
import vpc.core.Program;
import vpc.core.ProgramDecl;
import vpc.core.Value;
import vpc.core.base.PrimChar;
import vpc.core.base.PrimInt32;
import vpc.core.decl.Method;
import vpc.core.types.Type;
import vpc.dart.DartInterpreter;
import vpc.dart.ExternalVariable;
import vpc.dart.PathChecker;
import vpc.dart.RandomGenerator;
import vpc.dart.exception.ArgumentTypeNotSupport;
import vpc.dart.symb.exp.SymbExpBase;
import vpc.dart.symb.exp.SymbExpVariable;
import vpc.dart.symb.solver.ChocoSolver;
import vpc.dart.symb.solver.SymbolicSolver;
import vpc.dart.symc.SCValue;
import vpc.simu.IntptrScheduler;
import vpc.simu.RegisterPackage;
import vpc.stack.InterruptGraph;
import vpc.stack.StackSizeMonitor;
import vpc.util.SimpleLogger;

public class DartHarness {
	private static RandomGenerator s_randGen = new RandomGenerator();
	private Program m_program;
	private ExternalVariable m_extVars;
	private PathChecker m_checker;

	private Method findEntryMethod(String entryPointName) {
		return ProgramDecl.lookupEntryPoint(entryPointName, m_program).method;
	}
	
	protected SCValue[] randomArguments(Method method) {
		
		SCValue[] ret = new SCValue[method.getParameterTypes().length];
		for(int i = 0; i < ret.length; i++) {
			Type argtype = method.getParameterTypes()[i].getType();
			Value r = null;
			if(argtype.equals(PrimInt32.TYPE)) {
				r = PrimInt32.toValue(s_randGen.nextInt());
			} else if (argtype.equals(PrimChar.TYPE)) {
				r = PrimChar.toValue(s_randGen.nextChar());
			} else {
				throw new ArgumentTypeNotSupport(argtype.name);
			}
			ret[i] = new SCValue(r, new SymbExpVariable(i, argtype));
		}
		m_extVars.clear();
		m_extVars.readFromVariableList(ret);
		return ret;
	}
	protected SCValue[] directedArguments(Method method, SymbolicSolver solver, SCValue[] last) {
		if(last == null)
			last = randomArguments(method);
		last = m_extVars.combineToVariableList(last);
		solver.fillResVec(last);
		m_extVars.readFromVariableList(last);
		return last;
	}
	protected void runTests(DartInterpreter interp,  Method entry, int randomSize) {
		
		boolean forceRandom = true, isLinear = false;
		//SymbolicSolver solver = new SimpleSymbolicSolver();
		SymbolicSolver solver = new ChocoSolver();
		SimpleLogger.getLogger().newline()
		.info("=================================")
		.info("begin dart testing for entrance:").info(entry.getName());
		
		while(!isLinear && randomSize-- > 0) {
			m_checker.reset();
			SCValue[] args = null;
			do {
				SimpleLogger.getLogger().newline().info("new testing");

				try {
					if(forceRandom) {
						args = randomArguments(entry);
						SimpleLogger.getLogger().logValue("random input", args);
					} else {
						args = directedArguments(entry, solver, args);
						SimpleLogger.getLogger().logValue("directed input", args);
					}
					
					m_checker.setBegin(args);
					
					SimpleLogger.getLogger().info("begin dart execution");
					Value ret = interp.invokeComponentMethod(entry, Arrays.copyOf(args, entry.getParameterTypes().length));
					forceRandom = false;
					isLinear = m_checker.isLinear();
					
					SimpleLogger.getLogger()
						.logValue("return value", ret).logValue("linear", isLinear).logValue("path", m_checker);
					
					SymbExpBase[] cons = m_checker.genNextSymbolicConstrants();
					while(cons != null && !solver.solve(cons))
						cons = m_checker.genNextSymbolicConstrants();
					
					if(cons == null)
						forceRandom = true;
					
					SimpleLogger.getLogger().logValue("constraints solving", cons != null);
				} catch (Util.Error e) {
					SimpleLogger.getLogger().info("testing runtime error")
						.logValue("error msg", e.getMessage());
					e.printStackTrace();
					
					forceRandom = true;
					isLinear = false;
					//throw e;
				}
			} while(!forceRandom);
		}
		
		
	}
	protected void runTests(String entryPoint, int randomSize) {
		m_checker = new PathChecker();
		m_extVars = new ExternalVariable();
		InterruptGraph interGraph = new InterruptGraph();
		
		DartInterpreter interp = new DartInterpreter(m_program, m_checker, m_extVars);
		IntptrScheduler.Instance.initScheduler(interp, m_program);
		IntptrScheduler.Instance.initDartEnvi(m_checker, m_extVars);
		RegisterPackage state = IntptrScheduler.Instance.backupAllRegs(entryPoint), mainState = state;
		while(true) {
			interGraph.enterInterrupt(state);
			//m_interGraph.solveMaxPath(mainState);
			Method entry = findEntryMethod(entryPoint);
			
			StackSizeMonitor monitor = new StackSizeMonitor(entry, interGraph);
			interp.setMonitor(monitor);
			Interpreter.monitor = monitor;
			IntptrScheduler.Instance.initStackChecker(monitor, interGraph, entryPoint);
			
			runTests(interp, entry, randomSize);
			state = m_checker.getNextEntranceState();
			if(state == null)
				break;
			entryPoint = state.getEntryName();
			IntptrScheduler.Instance.restoreAllRegs(state);
		}
		SimpleLogger.getLogger().newline().info("===============================")
			.info("final stack size report");
		int stackSize = interGraph.solveMaxPath(mainState);
		SimpleLogger.getLogger().logValue("maximal stacksize cost", Math.abs(stackSize));
		if(stackSize < 0)
			SimpleLogger.getLogger().info("Attention: this is a infinite loop");
		SimpleLogger.getLogger().newline().info("===============================")
		.info("path information output");
		for(RegisterPackage res : interGraph.getPathInfo(mainState)) {
			SimpleLogger.getLogger().logValue("Entry", res.getEntryName()).newline();
			SimpleLogger.getLogger().logValue("Input value tracing", res.getInput()).newline();
		}

		
	}
	public DartHarness(Program program) {
		m_program = program;
	}
	public void startTest(String entryPointName) {
		runTests(entryPointName, 5);
	}
	
}
