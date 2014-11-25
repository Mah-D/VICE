package vpc.dart.symb.operator.compare;

import vpc.core.base.PrimChar;
import vpc.core.base.PrimInt32;

@SuppressWarnings("unchecked")
public class SymbCompLessEqual extends SymbCompBase {
	
	@Override
	public SymbCompBase getInverseOp() {
		return (SymbCompBase) getInsByClass(SymbCompGreater.class);
	}

	@Override
	public String getName() {
		return "LessEqual";
	}
	@Override
	public void register() {
		setOperator(this, PrimChar.LTEQ.class, PrimInt32.LTEQ.class);
	}

}
