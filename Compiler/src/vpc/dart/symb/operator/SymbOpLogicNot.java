package vpc.dart.symb.operator;

import vpc.core.Value;
import vpc.core.base.PrimBool;
import vpc.dart.exception.DartNotImplemented;
import vpc.dart.symb.exp.SymbExpBase;
import vpc.dart.symb.exp.SymbExpConst;
import vpc.dart.symb.exp.SymbExpOp;
import vpc.dart.symb.operator.compare.SymbCompBase;

public class SymbOpLogicNot extends SymbOperator {

	@Override
	public SymbExpBase computeSymbolic(Value conc, SymbExpBase... args) {
		assert args.length == 1;
		
		if(args[0] instanceof SymbExpConst) {
			return new SymbExpConst(
					SymbExpConst.class.cast(args[0]).getValue().equals(PrimBool.TRUE) ? PrimBool.FALSE : PrimBool.TRUE, true);
		} else if(args[0] instanceof SymbExpOp) {
			SymbExpOp opExp = SymbExpOp.class.cast(args[0]);
			if(opExp.operator instanceof SymbCompBase) {
				return new SymbExpOp(SymbCompBase.class.cast(opExp.operator).getInverseOp(), opExp.operands);
			} else if(opExp.operator instanceof SymbOpLogicNot) {
				return opExp.operands[0];
			} else
				throw new DartNotImplemented("Unknow how to reduce NOT");
		} else {
			return new SymbExpOp(this, args);
		}
	}

	@Override
	public String getName() {
		return "LogicNot";
	}

	@Override
	public int getOperandSize() {
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void register() {
		setOperator(this, PrimBool.NOT.class);
	}

}
