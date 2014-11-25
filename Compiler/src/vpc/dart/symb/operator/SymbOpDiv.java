package vpc.dart.symb.operator;

import vpc.core.Value;
import vpc.core.base.PrimInt32;
import vpc.dart.symb.exp.SymbExpConst;
import vpc.dart.symb.exp.SymbExpBase;
import vpc.dart.symb.exp.SymbExpOp;
import vpc.dart.symb.exp.SymbExpProperty;

public class SymbOpDiv extends SymbOperator {

	@Override
	public SymbExpBase computeSymbolic(Value conc, SymbExpBase... args) {
		assert args.length == 2;
		
		if(args[1] instanceof SymbExpConst) {
			if(args[0] instanceof SymbExpConst)
				return new SymbExpConst(conc, args[0].getExpProp() == SymbExpProperty.Linear && args[1].getExpProp() == SymbExpProperty.Linear);
			return new SymbExpOp(getThisOperator(), args);	
		} else
			return new SymbExpConst(conc, false);
	}

	@Override
	public String getName() {
		return "Divid";
	}

	@Override
	public int getOperandSize() {
		return 2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void register() {
		setOperator(this, PrimInt32.DIV.class);
	}

}
