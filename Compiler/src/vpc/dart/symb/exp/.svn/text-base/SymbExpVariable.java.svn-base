package vpc.dart.symb.exp;

import vpc.core.types.Type;

public class SymbExpVariable extends SymbExpBase {
	protected int m_index;
	protected Type m_type;
	
	public String toString() {
		return "(Var" + Integer.toString(m_index) + ":" + m_type.name + ")";
	}

	public SymbExpProperty getExpProp() {
		return SymbExpProperty.Linear;
	}
	public SymbExpVariable(int index, Type type) {
		m_index = index;
		m_type = type;
	}
	public int getId() {
		return m_index;
	}
	public Object toExpObject(SymbExpGeneratorI gen) {
		return gen.generate(this);
	}
}
