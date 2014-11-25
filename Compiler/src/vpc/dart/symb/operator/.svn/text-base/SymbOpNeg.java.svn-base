package vpc.dart.symb.operator;

import vpc.core.Value;
import vpc.core.base.PrimInt32;
import vpc.dart.symb.exp.SymbExpBase;
import vpc.dart.symb.exp.SymbExpConst;

public class SymbOpNeg extends SymbOperator {

	@Override
	public SymbExpBase computeSymbolic(Value conc, SymbExpBase... args) {
		assert args.length == 1;
		if(args[0] instanceof SymbExpConst)
			return new SymbExpConst(conc, true);
		else {
			return SymbOperator.getInsByClass(SymbOpSub.class).computeSymbolic(conc,
					new SymbExpConst(PrimInt32.toValue(0), true), args[0]);
		}
	}

	@Override
	public String getName() {
		return "Negtive";
	}

	@Override
	public int getOperandSize() {
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void register() {
		setOperator(this, PrimInt32.NEG.class);
	}

}
