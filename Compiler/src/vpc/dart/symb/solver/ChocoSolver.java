package vpc.dart.symb.solver;

import java.util.Iterator;

import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.integer.IntegerConstantVariable;
import choco.kernel.model.variables.integer.IntegerVariable;
import vpc.core.base.PrimInt32;
import vpc.dart.symb.exp.SymbExpBase;
import vpc.util.SimpleLogger;

public class ChocoSolver extends SymbolicSolver {

	@Override
	protected boolean solveInternal(SymbExpBase[] constraints) {
		CPModel model = new CPModel();
		ChocoExpGenerator gen = new ChocoExpGenerator(model);
		for(SymbExpBase exp : constraints)
			model.addConstraint(Constraint.class.cast(exp.toExpObject(gen)));
		CPSolver solver = new CPSolver();
		try {
			solver.read(model);
			boolean res = solver.solve();
			if(res) {
				Iterator<IntegerVariable> itr = model.getIntVarIterator();
				while(itr.hasNext()) {
					IntegerVariable var = itr.next();
					if(!(var instanceof IntegerConstantVariable)) {
						putResult(Integer.parseInt(var.getName()), 
								PrimInt32.toValue(solver.getVar(var).getVal()));
					}
				}
			}
			return res;
		} catch(Throwable err) {
			SimpleLogger.getLogger().error("Can't solve it! " + err.toString());
			return false;
		}
	}

}
