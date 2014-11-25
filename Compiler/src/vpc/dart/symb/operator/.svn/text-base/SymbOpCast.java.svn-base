package vpc.dart.symb.operator;

import vpc.core.Value;
import vpc.core.base.PrimConversion;
import vpc.dart.symb.exp.SymbExpBase;

public class SymbOpCast extends SymbOperator {

	@Override
	public SymbExpBase computeSymbolic(Value conc, SymbExpBase... args) {
		assert args.length == 1;
		return args[0]; // right now, we do nothing. suppose all the conversion is casting
	}

	@Override
	public String getName() {
		return "Cast";
	}

	@Override
	public int getOperandSize() {
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void register() {
		setOperator(this, PrimConversion.Char_Int32.class, PrimConversion.Raw_Int32.class, PrimConversion.AdjustRaw.class);

	}

}
