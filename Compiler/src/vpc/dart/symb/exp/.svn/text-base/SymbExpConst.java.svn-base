package vpc.dart.symb.exp;

import vpc.core.Value;
import vpc.core.base.PrimBool;
import vpc.core.base.PrimChar;
import vpc.core.base.PrimInt32;
import vpc.core.types.Type;
import vpc.dart.exception.DartNotImplemented;


public class SymbExpConst extends SymbExpBase {
	protected boolean m_isLinear;
	protected Value m_val;
	public String toString() {
		Type type = m_val.getType();
		if(type.equals(PrimInt32.TYPE))
			return "(" + Integer.toString(PrimInt32.fromValue(m_val)) + ")";
		else if(type.equals(PrimBool.TYPE))
			return "(" + Boolean.toString(PrimBool.fromValue(m_val)) + ")";
		else if(type.equals(PrimChar.TYPE))
			return "(" + Character.toString(PrimChar.fromValue(m_val)) + ")";
		else
			return "<unknow type>";
	}
	public Value getValue() {
		return m_val;
	}
	public SymbExpConst(Value conc, boolean isLinear) {
		m_val = conc;
		m_isLinear = isLinear;
	}
	public SymbExpProperty getExpProp() {
		return m_isLinear ? SymbExpProperty.Linear : SymbExpProperty.NonLinear;
	}
	public Type getExpType() {
		return m_val.getType();
	}
	@Override
	public Object toExpObject(SymbExpGeneratorI gen) {
		return gen.generate(this);
	}
}
