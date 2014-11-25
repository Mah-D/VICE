package vpc.dart.symb.solver;

import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import vpc.core.Value;
import vpc.dart.symb.exp.SymbExpBase;
import vpc.dart.symc.SCValue;

public abstract class SymbolicSolver {
	protected Map<Integer, Value> m_res = new TreeMap<Integer, Value>();
	
	public boolean solve(SymbExpBase[] constraints) {
		m_res.clear();
		return solveInternal(constraints);
	}
	
	public void fillResVec(SCValue[] val) {
		for(Entry<Integer, Value> entry : m_res.entrySet()) {
			val[entry.getKey()] = new SCValue(entry.getValue(), val[entry.getKey()].getSymbolicValue());
		}
	}
	protected abstract boolean solveInternal(SymbExpBase[] constraints);
	protected void putResult(int index, Value val) {
		m_res.put(index, val);
	}
}
