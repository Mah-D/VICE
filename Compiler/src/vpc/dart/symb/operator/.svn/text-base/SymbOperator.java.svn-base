package vpc.dart.symb.operator;

import java.util.HashMap;

import vpc.core.Value;
import vpc.dart.symb.exp.SymbExpBase;
import vpc.tir.expr.Operator;

public abstract class SymbOperator {
	private static HashMap<Class<? extends Operator>, SymbOperator> s_SymbolicOps = new HashMap<Class<? extends Operator>, SymbOperator>();
	private static HashMap<Class<? extends SymbOperator>, SymbOperator> s_SymbolicOpInstance = new HashMap<Class<? extends SymbOperator>, SymbOperator>();
	public abstract SymbExpBase computeSymbolic(Value conc, SymbExpBase ...args);
	public abstract String getName();
	public abstract int getOperandSize();
	public abstract void register();
	/**
	 * get correspond symbolic operator from concrete operator
	 * @param op: concrete operator
	 * @return symbolic operator instance
	 */
	public static SymbOperator getOperator(Class<? extends Operator> op) {
		
		return s_SymbolicOps.get(op);
	}
	
	/**
	 * get the single instance of assigned symbolic operator
	 * @param op: the Class of the symbolic operator
	 * @return single instance of that operator
	 */
	public static SymbOperator getInsByClass(Class<? extends SymbOperator> op) {
		return s_SymbolicOpInstance.get(op);
	}
	
	protected static void setOperator(SymbOperator sOp, Class<? extends Operator> ...cOps) {
		s_SymbolicOpInstance.put(sOp.getClass(), sOp);
		for(Class<? extends Operator> cOp : cOps)
			s_SymbolicOps.put(cOp, sOp);
	}
	
	protected SymbOperator getThisOperator() {
		return getInsByClass(this.getClass());
	}
	public String toString() {
		return getName();
	}
}
