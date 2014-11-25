package vpc.dart.symb.operator.compare;

import vpc.core.Value;
import vpc.dart.symb.exp.SymbExpConst;
import vpc.dart.symb.exp.SymbExpBase;
import vpc.dart.symb.exp.SymbExpOp;
import vpc.dart.symb.exp.SymbExpProperty;
import vpc.dart.symb.operator.SymbOperator;

public abstract class SymbCompBase extends SymbOperator {
	public abstract SymbCompBase getInverseOp();
	public int getOperandSize() {
		return 2;
	}
	@Override
	public SymbExpBase computeSymbolic(Value conc, SymbExpBase... args) {
		assert args.length == 2;
		if(args[0] instanceof SymbExpConst && args[1] instanceof SymbExpConst) {
			return new SymbExpConst(conc, args[0].getExpProp() == SymbExpProperty.Linear && args[1].getExpProp() == SymbExpProperty.Linear);
		}
		return new SymbExpOp(getThisOperator(), args);
	}
}
