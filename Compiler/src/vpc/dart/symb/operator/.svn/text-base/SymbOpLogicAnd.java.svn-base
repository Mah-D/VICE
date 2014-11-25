package vpc.dart.symb.operator;

import vpc.core.Value;
import vpc.core.base.PrimBool;
import vpc.dart.symb.exp.SymbExpBase;
import vpc.dart.symb.exp.SymbExpConst;
import vpc.dart.symb.exp.SymbExpOp;

public class SymbOpLogicAnd extends SymbOperator {

	@Override
	public SymbExpBase computeSymbolic(Value conc, SymbExpBase... args) {
		assert args.length == 2;
		
		if(args[0] instanceof SymbExpConst) {
			if(SymbExpConst.class.cast(args[0]).getValue().equals(PrimBool.TRUE))
				return args[1];
			else
				return new SymbExpConst(PrimBool.FALSE, true);
		} else if(args[1] instanceof SymbExpConst) {
			if(SymbExpConst.class.cast(args[1]).getValue().equals(PrimBool.TRUE))
				return args[0];
			else
				return new SymbExpConst(PrimBool.FALSE, true);
		} else
			return new SymbExpOp(this, args);
	}

	@Override
	public String getName() {
		return "LogicAnd";
	}

	@Override
	public int getOperandSize() {
		return 2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void register() {
		setOperator(this, PrimBool.AND.class);
	}

}

