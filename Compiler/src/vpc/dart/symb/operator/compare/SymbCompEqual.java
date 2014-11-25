package vpc.dart.symb.operator.compare;

import vpc.core.Value;
import vpc.core.base.PrimChar;
import vpc.core.base.PrimInt32;

@SuppressWarnings("unchecked")
public class SymbCompEqual extends SymbCompBase {
	
	@Override
	public SymbCompBase getInverseOp() {
		return (SymbCompBase)getInsByClass(SymbCompNotEqual.class);
	}

	@Override
	public String getName() {
		return "Equal";
	}

	@Override
	public void register() {
		setOperator(this, PrimChar.EQU.class, PrimInt32.EQU.class, Value.Equal.class);
	}


}
