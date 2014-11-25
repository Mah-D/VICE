package vpc.dart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vpc.core.Value;
import vpc.core.base.PrimChar;
import vpc.core.base.PrimInt32;
import vpc.core.base.PrimRaw;
import vpc.core.types.Type;
import vpc.dart.exception.ArgumentTypeNotSupport;
import vpc.dart.symb.exp.SymbExpVariable;
import vpc.dart.symc.SCValue;

public class ExternalVariable {
	protected int m_indexOffset;
	protected List<SCValue> m_variables = new ArrayList<SCValue>();
	protected Map<String, Integer> m_nameMapper = new HashMap<String, Integer>();
	public void clear(int len) {
		m_indexOffset = len;
		m_variables.clear();
		m_nameMapper.clear();
	}
	public void clear() {
		clear(0);
	}
	
	public SCValue getValue(String name, Type t) {
		if(m_nameMapper.containsKey(name))
			return m_variables.get(m_nameMapper.get(name));
		else
			return genNewValue(name, t);
	}
	public void setValue(String name, Value v) {
		SCValue val = SCValue.getSCValue(v);
		if(!m_nameMapper.containsKey(name))
			genNewValue(name, val.getType());
		m_variables.set(m_nameMapper.get(name), val);
	}
	public SCValue getRaw8(String name) {
		return getValue(name, PrimRaw.getType(8));
	}
	protected Value getRandomValue(Type t) {
		Value r;
		if(t.equals(PrimInt32.TYPE)) {
			r = PrimInt32.toValue(RandomGenerator.getIns().nextInt());
		} else if (t.equals(PrimChar.TYPE)) {
			r = PrimChar.toValue(RandomGenerator.getIns().nextChar());
		} else if (t.equals(PrimRaw.getType(8))){
			r = PrimRaw.toValue(8, RandomGenerator.getIns().nextInt(256));
		} else if(t.equals(PrimRaw.getType(1))) {
			r = PrimRaw.toValue(1, RandomGenerator.getIns().nextInt(2));
		} else if(t instanceof PrimRaw.IType) {
			r = PrimRaw.toValue(((PrimRaw.IType) t).width, RandomGenerator.getIns().nextInt(1 << PrimRaw.IType.class.cast(t).width));
		} else {
			throw new ArgumentTypeNotSupport(t.name);
		}
		return r;
	}
	protected SCValue genNewValue(String name, Type t) {
		m_nameMapper.put(name, m_variables.size());
		SCValue ret = new SCValue(getRandomValue(t), new SymbExpVariable(m_indexOffset, t));
		m_variables.add(ret);
		++m_indexOffset;
		return ret;
	}
	public void readFromVariableList(SCValue[] list) {
		m_indexOffset = list.length;
		for(int i = 0, beg = m_indexOffset - m_variables.size(); i < m_variables.size(); i++) {
			if(list[i + beg].getSymbolicValue() instanceof SymbExpVariable)
				m_variables.set(i, list[i + beg]);
			else {
				Type t = list[i + beg].getType();
				m_variables.set(i, new SCValue(getRandomValue(t), 
						new SymbExpVariable(i + beg, t)));
			}
		}
	}
	
	public SCValue[] combineToVariableList(SCValue[] argList) {
		SCValue[] ret;
		
		if(m_variables.isEmpty())
			return argList;
		else if(argList.length == m_indexOffset)
			ret = argList;
		else
			ret = Arrays.copyOf(argList, m_indexOffset);
		assert argList.length <= m_indexOffset;
		System.arraycopy(m_variables.toArray(), 0, ret, m_indexOffset - m_variables.size(), m_variables.size());
		return ret;
	}
	
}
