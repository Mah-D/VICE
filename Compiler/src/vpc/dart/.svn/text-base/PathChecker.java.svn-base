package vpc.dart;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import vpc.core.base.PrimBool;
import vpc.dart.symb.exp.SymbExpBase;
import vpc.dart.symb.exp.SymbExpConst;
import vpc.dart.symb.exp.SymbExpProperty;
import vpc.dart.symb.operator.SymbOpLogicNot;
import vpc.dart.symb.operator.SymbOperator;
import vpc.dart.symc.SCValue;
import vpc.simu.RegisterPackage;


public class PathChecker {
	protected static class PathConstraint {
		boolean searched, direction;
		short blockIndex;
		SymbExpBase cond;
		PathConstraint(boolean direction, SymbExpBase cond) {
			searched = false;
			this.direction = direction; 
			this.cond = cond;
		}
		PathConstraint(boolean direction, SymbExpBase cond, short bId) {
			this(direction, cond);
			blockIndex = bId;
		}
		boolean next() {
			if(searched)
				return false;
			searched = true;
			direction = !direction;
			inverseSymbolicConstraint();
			return true;
		}
		void inverseSymbolicConstraint() {
			cond = SymbOperator.getInsByClass(SymbOpLogicNot.class).computeSymbolic(
					PrimBool.TRUE, cond);
		}
	}
	protected LinkedList<PathConstraint> m_rec = new LinkedList<PathConstraint>(); //TODO: need to replace with deque
	protected Iterator<PathConstraint> m_pos;
	protected Map<RegisterPackage, Boolean> m_coverdEntrance = new HashMap<RegisterPackage, Boolean>();
	protected SCValue[] m_inputVec;
	protected short m_curBlockIndex, m_interruptDisable;
	protected int m_stackSizeLimit = 10000;
	
	public SCValue[] getCurrentInput() {
		return m_inputVec;
	}
	public PathChecker () {
		reset();
	}
	public PathChecker (int stackSize) {
		this();
		m_stackSizeLimit = stackSize;
	}
	public void setBegin() {
		m_pos = m_rec.iterator();
		m_curBlockIndex = 0;
	}
	public void setBegin(SCValue[] input) {
		setBegin();
		m_inputVec = input;
	}
	public void reset() {
		m_rec.clear();
		m_interruptDisable = 0;
		m_curBlockIndex = 0;
		m_pos = m_rec.iterator();
	}
	public boolean isLinear() {
		for(PathConstraint con : m_rec) {
			if(con.cond.getExpProp() == SymbExpProperty.NonLinear)
				return false;
		}
		return true;
	}
	
	public SymbExpBase[] genNextSymbolicConstrants() {
		
		while(!m_rec.isEmpty() && !m_rec.getLast().next())
			m_rec.removeLast();
		if(m_rec.isEmpty())
			return null;
		
		SymbExpBase[] cons = new SymbExpBase[m_rec.size()];
		
		Iterator<PathConstraint> itr = m_rec.iterator();
		for(int i = 0; i < m_rec.size(); i++)
			cons[i] = itr.next().cond;
		return cons;
	}
	
	public void enterBlock() {
		++m_curBlockIndex;
	}
	public void exitBlock() {
		--m_curBlockIndex;
	}
	public RegisterPackage getNextEntranceState() {
		for(Entry<RegisterPackage, Boolean> item : m_coverdEntrance.entrySet()) {
			if(!item.getValue()) {
				item.setValue(true);
				return item.getKey();
			}
		}
		return null;
	}
	public void enterInterrupt(RegisterPackage states) {
		if(isTracing() && !m_coverdEntrance.containsKey(states))
			m_coverdEntrance.put(states, false);
		m_interruptDisable++;
	}
	public boolean isTracing() {
		return m_interruptDisable == 0;
	}
	public void exitInterrupt() {
		m_interruptDisable--;
	}
	public boolean checkAndStep(SCValue cond) {
		assert cond.getType().equals(PrimBool.TYPE);
		
		// do nothing if in the interrupt or it has nothing to do with input
		if(!isTracing() || cond.getSymbolicValue() instanceof SymbExpConst)
			return true;
		
		boolean res = PrimBool.fromValue(cond.getConcreteValue());
		if(m_pos.hasNext())
			return m_pos.next().direction == res;
		else {
			if(m_rec.size() >= m_stackSizeLimit)
				return true;
			m_rec.add(new PathConstraint(PrimBool.fromValue(cond.getConcreteValue()), cond.getSymbolicValue(), m_curBlockIndex));
			if(!res)
				m_rec.getLast().inverseSymbolicConstraint();
			m_pos = m_rec.listIterator(m_rec.size());
			return true;
		}
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder("path constraints: ");
		Iterator<PathConstraint> itr = m_rec.iterator();
		int deep = 0;
		while(itr.hasNext()) {
			str.append(' ');
			PathConstraint cur = itr.next();
			while(cur.blockIndex > deep) {
				str.append('(');
				++deep;
			}
			
			str.append(cur.direction);
			str.append(cur.blockIndex);
			while(cur.blockIndex < deep) {
				str.append(')');
				--deep;
			}
			deep = cur.blockIndex;
		}
		while(deep-- > 0)
			str.append(')');
		return str.toString();
	}
}
