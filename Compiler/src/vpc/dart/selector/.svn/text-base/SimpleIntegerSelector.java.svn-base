package vpc.dart.selector;

import vpc.core.Value;
import vpc.core.base.PrimBool;
import vpc.core.base.PrimInt32;
import vpc.tir.expr.Operator;
import vpc.tir.expr.Operator.Op0;
import vpc.tir.expr.Operator.Op1;
import vpc.tir.expr.Operator.Op2;

public class SimpleIntegerSelector implements InstrumentSelectorI {

	public boolean isInsteresting(Value val) {
		return val.getType().equals(PrimInt32.TYPE) || val.getType().equals(PrimBool.TYPE);
	}

	public boolean isInsteresting(Operator op) {
		return (op instanceof Op2 || op instanceof Op1) && (op.getOperandTypes()[0].equals(PrimInt32.TYPE) || op.getOperandTypes()[0].equals(PrimBool.TYPE));
	}
}
