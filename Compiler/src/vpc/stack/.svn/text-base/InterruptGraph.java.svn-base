package vpc.stack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import vpc.simu.RegisterPackage;

public class InterruptGraph {
	protected final static int LoopTimes = 2;
	protected static class GraphNode {
		RegisterPackage interruptPoint, maximalFirePoint;
		Map<GraphNode, Integer> nextNodes;
		GraphNode lastNode;
		int visitTag, maximalSize;
		GraphNode(RegisterPackage regs) {
			interruptPoint = regs;
			nextNodes = new HashMap<GraphNode, Integer>();
		}
		void setPath(GraphNode node, int size, RegisterPackage firePoint) {
			Integer last = nextNodes.put(node, size);
			if(last != null && last > size)
				nextNodes.put(node, last);
			if(size > maximalSize) {
				maximalSize = size;
				maximalFirePoint = firePoint;
			}
		}
		
	}
	
	protected Map<RegisterPackage, GraphNode> m_allStates = new HashMap<RegisterPackage, GraphNode>();
	protected GraphNode m_currentState, m_maxPath;
	
	public RegisterPackage[] getPathInfo(RegisterPackage start) {
		ArrayList<RegisterPackage> res = new ArrayList<RegisterPackage>();
		if(m_maxPath != null) {
			res.add(m_maxPath.maximalFirePoint);
			m_maxPath = m_maxPath.lastNode;
		}
		int limit = m_allStates.values().size() * LoopTimes;
		while(--limit > 0 && m_maxPath != null) {
			res.add(m_maxPath.interruptPoint);
			m_maxPath = m_maxPath.lastNode;
		}
		if(m_maxPath != null)
			res.add(start);
		Collections.reverse(res);
		return res.toArray(new RegisterPackage[0]);
	}
	
	public void updateCurrentSize(int size, RegisterPackage point) {
		if(size > m_currentState.maximalSize) {
			m_currentState.maximalSize = size;
			m_currentState.maximalFirePoint = point;
		}
	}
	public void enterInterrupt(RegisterPackage regs) {
		if(!m_allStates.containsKey(regs))
			m_allStates.put(regs, m_currentState = new GraphNode(regs));
		else
			m_currentState = m_allStates.get(regs);
	}
	public void addConnection(RegisterPackage pag, int size) {
		if(!m_allStates.containsKey(pag))
			m_allStates.put(pag, new GraphNode(pag));
		m_currentState.setPath(m_allStates.get(pag), size, pag);
	}
	
	// a limit is set when infinite loop appear
	// if infinit loop found, return negtive value
	public int solveMaxPath(RegisterPackage start) {
		assert m_allStates.containsKey(start);

		for(GraphNode node: m_allStates.values()) {
			node.visitTag = -1;
			node.lastNode = null;
		}
		
		m_allStates.get(start).visitTag = 0;
		m_maxPath = m_allStates.get(start);
		// double loop size
		int times = m_allStates.values().size() * LoopTimes, maxValue = m_allStates.get(start).maximalSize;
		boolean stopped = false;
		while(!stopped && times-- > 0) {
			stopped = true;
			for(GraphNode node: m_allStates.values()) {
				if(node.visitTag < 0)
					continue;
				else {
					for(Entry<GraphNode, Integer> subs : node.nextNodes.entrySet()) {
						if(subs.getKey().visitTag < subs.getValue() + node.visitTag) {
							stopped = false;
							subs.getKey().lastNode = node;
							subs.getKey().visitTag = subs.getValue() + node.visitTag;
							if(subs.getKey().maximalSize + subs.getKey().visitTag >	maxValue) {
								maxValue = subs.getKey().maximalSize + subs.getKey().visitTag;
								m_maxPath = subs.getKey();
							}
						}
					}
				}
			}
		}
		return stopped ? maxValue : -maxValue;
	}
}
