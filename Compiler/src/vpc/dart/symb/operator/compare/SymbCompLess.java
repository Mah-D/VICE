package vpc.dart.symb.operator.compare;

import vpc.core.base.PrimChar;
import vpc.core.base.PrimInt32;

@SuppressWarnings("unchecked")
public class SymbCompLess extends SymbCompBase {
	@Override
	public SymbCompBase getInverseOp() {
		return (SymbCompBase) getInsByClass(SymbCompGreaterEqual.class);
	}

	@Override
	public String getName() {
		return "Less";
	}

	@Override
	public void register() {
		setOperator(this, PrimChar.LT.class, PrimInt32.LT.class);		
	}

}
