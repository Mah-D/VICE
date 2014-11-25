package vpc.dart.symb.operator.compare;

import vpc.core.base.PrimChar;
import vpc.core.base.PrimInt32;

@SuppressWarnings("unchecked")
public class SymbCompGreater extends SymbCompBase {

	@Override
	public SymbCompBase getInverseOp() {
		return (SymbCompBase) getInsByClass(SymbCompLessEqual.class);
	}

	@Override
	public String getName() {
		return "Greater";
	}

	@Override
	public void register() {
		setOperator(this, PrimChar.GR.class, PrimInt32.GR.class);		
	}
}
