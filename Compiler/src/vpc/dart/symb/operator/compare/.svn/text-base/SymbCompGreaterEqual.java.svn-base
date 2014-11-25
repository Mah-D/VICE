package vpc.dart.symb.operator.compare;

import vpc.core.base.PrimChar;
import vpc.core.base.PrimInt32;

@SuppressWarnings("unchecked")
public class SymbCompGreaterEqual extends SymbCompBase {

	@Override
	public SymbCompBase getInverseOp() {
		return (SymbCompBase)SymbCompGreaterEqual.getInsByClass(SymbCompLess.class);
	}

	@Override
	public String getName() {
		return "GreaterEqual";
	}

	@Override
	public void register() {
		setOperator(this, PrimChar.GREQ.class, PrimInt32.GREQ.class);		
	}
}
