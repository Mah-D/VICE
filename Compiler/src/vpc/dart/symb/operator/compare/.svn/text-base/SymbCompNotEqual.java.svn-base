package vpc.dart.symb.operator.compare;

import vpc.core.Value;
import vpc.core.base.PrimChar;
import vpc.core.base.PrimInt32;


@SuppressWarnings("unchecked")
public class SymbCompNotEqual extends SymbCompBase {

	@Override
	public SymbCompBase getInverseOp() {
		return (SymbCompBase) getInsByClass(SymbCompEqual.class);
	}

	@Override
	public String getName() {
		return "NotEqual";
	}

	@Override
	public void register() {
		setOperator(this, PrimChar.NEQU.class, PrimInt32.NEQU.class, Value.NotEqual.class);
		
	}


}
