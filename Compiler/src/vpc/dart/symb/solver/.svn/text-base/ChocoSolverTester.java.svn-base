package vpc.dart.symb.solver;

import choco.Choco;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.Model;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.Solver;

public final class ChocoSolverTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Model m = new CPModel();
		IntegerVariable v1 = Choco.makeIntVar("1", Short.MIN_VALUE, Short.MAX_VALUE);
		//IntegerVariable v2 = Choco.makeIntVar("2", Integer.MIN_VALUE, Integer.MAX_VALUE);
		
		IntegerVariable c1 = Choco.makeConstantVar("c1", 45);
		IntegerVariable c3 = Choco.makeConstantVar("c3", 255);
		IntegerVariable c2 = Choco.makeConstantVar("c2", 5000);
		
		m.addVariable(v1);
		m.addConstraint(Choco.TRUE);
		m.addConstraint(Choco.neq(v1, c3));
		m.addConstraint(Choco.gt(v1, c1));
		m.addConstraint(Choco.gt(v1, c2));
		
		Solver s = new CPSolver();
		s.read(m);
		s.solve();
		
		System.out.println(m.solutionToString());
		System.out.println(s.getVar(v1).getVal());
		
	}

}
