package vpc.dart.symc;

import vpc.core.Value;
import vpc.core.types.Type;
import vpc.dart.symb.exp.SymbExpBase;
import vpc.dart.symb.exp.SymbExpConst;

public class SCValue extends Value implements Cloneable {
	private Value m_concreteValue = null;
	private SymbExpBase m_symbExp = null;
	
	
	public static Value getConcreteValue(Value val) {
		if(val instanceof SCValue)
			return SCValue.class.cast(val).getConcreteValue();
		else
			return val;
	}
	public static SCValue getSCValue(Value val) {
		if(val instanceof SCValue)
			return SCValue.class.cast(val);
		else
			return new SCValue(val, true);
	}
	public static SCValue[] getSCValues(Value[] val) {
		if(val instanceof SCValue[])
			return SCValue[].class.cast(val);
		else {
			SCValue[] ret = new SCValue[val.length];
			for(int i = 0; i < val.length; i++)
				ret[i] = new SCValue(val[i], true);
			return ret;
		}
	}

	@Override
	public Type getType() {
		if(m_concreteValue == Value.BOTTOM)
			return null;
		return m_concreteValue.getType();
	}

	public Value getConcreteValue() {
		return m_concreteValue;
	}
	public SymbExpBase getSymbolicValue() {
		return m_symbExp;
	}
	public static SymbExpBase[] getSymbolicValues(SCValue[] values) {
		SymbExpBase[] ret = new SymbExpBase[values.length];
		for(int i = 0; i < values.length; i++) {
			ret[i] = values[i].getSymbolicValue();
		}
		return ret;
	}
	public static Value[] getConcreteValues(Value[] values) {
		if(values instanceof SCValue[]) {
			Value[] ret = new Value[values.length];
			for(int i = 0; i < values.length; i++) {
				ret[i] = SCValue.class.cast(values[i]).getConcreteValue();
			}
			return ret;
		} else
			return values;
	}
	public SCValue(Value val, SymbExpBase exp) {
		assert val != null;
		assert !(val instanceof SCValue) : "value must be concrete";
		m_concreteValue = val;
		m_symbExp = SymbolicConcreteSolver.solve(exp, val);
	}
	public SCValue(SymbExpBase exp, Type type) {
		assert exp != null;
		m_concreteValue = new EmptyValue(type);
		m_symbExp = exp;
	}
	public SCValue(Value val, boolean isLinear) {
		if(val == null)
			val = Value.BOTTOM;
		if(val instanceof SCValue) {
			SCValue one = SCValue.class.cast(val);
			m_concreteValue = one.m_concreteValue;
			m_symbExp = one.m_symbExp;
		} else {
			m_concreteValue = val;
			m_symbExp = new SymbExpConst(val, isLinear);
		}
		
	}
	public SCValue(Value val) {
		if(val == null)
			val = Value.BOTTOM;
		if(val instanceof SCValue) {
			SCValue one = SCValue.class.cast(val);
			m_concreteValue = one.m_concreteValue;
			m_symbExp = one.m_symbExp;
		} else {
			m_concreteValue = val;
			m_symbExp = SymbolicConcreteSolver.solve(null, val);
		}
	}
	public String toString() {
//		if(m_symbExp == null)
//			System.out.println();
		if(m_concreteValue == null || m_symbExp == null)
			return "";
		return "SCValue: [concrete: " + m_concreteValue.toString() + "; symbolic: (" + m_symbExp.toString() + ")]";
	}
	public SCValue clone() {
		return new SCValue(m_concreteValue, m_symbExp);
	}
}

class EmptyValue extends Value {
	private Type m_type;
	public EmptyValue(Type type) {
		m_type = type;
	}
	@Override
	public Type getType() {
		return m_type;
	}
}
