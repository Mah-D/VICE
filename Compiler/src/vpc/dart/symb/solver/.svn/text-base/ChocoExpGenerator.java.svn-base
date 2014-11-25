package vpc.dart.symb.solver;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import choco.Choco;
import choco.kernel.model.Model;
import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.integer.IntegerExpressionVariable;
import choco.kernel.model.variables.integer.IntegerVariable;
import vpc.core.base.PrimBool;
import vpc.core.base.PrimInt32;
import vpc.dart.exception.DartNotImplemented;
import vpc.dart.symb.exp.*;
import vpc.dart.symb.operator.*;
import vpc.dart.symb.operator.compare.*;

public class ChocoExpGenerator implements SymbExpGeneratorI {
	protected final static int LowBound = Short.MIN_VALUE, UpBound = Short.MAX_VALUE;
	protected Model m_model;
	protected Map<Integer, IntegerVariable> m_variable = new TreeMap<Integer, IntegerVariable>();
	protected interface ChocoOperator {
		Object generate(Object[] args);
	}
	protected abstract static class ChocoIntegerOperator implements ChocoOperator {
		public Object generate(Object[] args) {
			assert args.length == 2 && args[0] instanceof IntegerExpressionVariable && args[1] instanceof IntegerExpressionVariable;
			return generate((IntegerExpressionVariable)args[0], (IntegerExpressionVariable)args[1]);
		}
		protected abstract Object generate(
				IntegerExpressionVariable arg1, IntegerExpressionVariable arg2);
	}
	protected abstract static class ChocoConstraintOperator implements ChocoOperator {
		public Constraint generate(Object[] args) {
			assert args instanceof Constraint[];
			return generate(Constraint[].class.cast(args));
		}
		protected abstract Constraint generate(
				Constraint[] args);
	}

	protected static class ChocoAdd extends ChocoIntegerOperator {
		protected IntegerExpressionVariable generate(
				IntegerExpressionVariable arg1, IntegerExpressionVariable arg2) {
			return Choco.plus(arg1, arg2);
		}
	}
	protected static class ChocoSub extends ChocoIntegerOperator {
		protected IntegerExpressionVariable generate(
				IntegerExpressionVariable arg1, IntegerExpressionVariable arg2) {
			return Choco.minus(arg1, arg2);
		}
	}
	protected static class ChocoMult extends ChocoIntegerOperator {
		protected IntegerExpressionVariable generate(
				IntegerExpressionVariable arg1, IntegerExpressionVariable arg2) {
			return Choco.mult(arg1, arg2);
		}
	}
	protected static class ChocoDiv extends ChocoIntegerOperator {
		protected IntegerExpressionVariable generate(
				IntegerExpressionVariable arg1, IntegerExpressionVariable arg2) {
			return Choco.div(arg1, arg2);
		}
	}
	protected static class ChocoLess extends ChocoIntegerOperator {
		protected Constraint generate(
				IntegerExpressionVariable arg1, IntegerExpressionVariable arg2) {
			return Choco.lt(arg1, arg2);
		}
	}
	protected static class ChocoGreater extends ChocoIntegerOperator {
		protected Constraint generate(
				IntegerExpressionVariable arg1, IntegerExpressionVariable arg2) {
			return Choco.gt(arg1, arg2);
		}
	}
	protected static class ChocoLessEqual extends ChocoIntegerOperator {
		protected Constraint generate(
				IntegerExpressionVariable arg1, IntegerExpressionVariable arg2) {
			return Choco.leq(arg1, arg2);
		}
	}
	protected static class ChocoGreaterEqual extends ChocoIntegerOperator {
		protected Constraint generate(
				IntegerExpressionVariable arg1, IntegerExpressionVariable arg2) {
			return Choco.geq(arg1, arg2);
		}
	}
	protected static class ChocoEqual extends ChocoIntegerOperator {
		protected Constraint generate(
				IntegerExpressionVariable arg1, IntegerExpressionVariable arg2) {
			return Choco.eq(arg1, arg2);
		}
	}
	protected static class ChocoNotEqual extends ChocoIntegerOperator {
		protected Constraint generate(
				IntegerExpressionVariable arg1, IntegerExpressionVariable arg2) {
			return Choco.neq(arg1, arg2);
		}
	}
	protected static class ChocoLogicAnd extends ChocoConstraintOperator {
		protected Constraint generate(Constraint[] args) {
			return Choco.and(args);
		}
	}
	protected static class ChocoLogicOr extends ChocoConstraintOperator {
		protected Constraint generate(Constraint[] args) {
			return Choco.or(args);
		}
	}
	protected static class ChocoLogicNot extends ChocoConstraintOperator {
		protected Constraint generate(Constraint[] args) {
			assert args.length == 1;
			return Choco.not(args[0]);
		}
	}
	
	Map<SymbOperator, ChocoOperator> map = new HashMap<SymbOperator, ChocoOperator>();
	public Object generate(SymbExpOp exp) {
		Object[] subs = new Object[exp.operands.length];
		for(int i = 0; i < exp.operands.length; i++)
			subs[i] = exp.operands[i].toExpObject(this);
		return map.get(exp.operator).generate(subs);
	}

	public Object generate(SymbExpVariable exp) {
		IntegerVariable v = m_variable.get(exp.getId());
		if(v == null) {
			v = Choco.makeIntVar(Integer.toString(exp.getId()), LowBound, UpBound);;
			m_variable.put(exp.getId(), v);
			m_model.addVariable(v);
		}
		return v;
	}

	public Object generate(SymbExpConst exp) {
		if(exp.getExpType().equals(PrimInt32.TYPE)) {
			IntegerVariable c = Choco.makeConstantVar(exp.getValue().toString(), PrimInt32.fromValue(exp.getValue()));
			m_model.addVariable(c);
			return c;
		} else if(exp.getExpType().equals(PrimBool.TYPE)) {
			return PrimBool.fromValue(exp.getValue()) ? Choco.TRUE : Choco.FALSE;
		} else
			throw new DartNotImplemented("generate " + exp.getExpType());
	}
	public ChocoExpGenerator(Model model) {
		m_model = model;
		map.put(SymbOperator.getInsByClass(SymbOpAdd.class), new ChocoAdd());
		map.put(SymbOperator.getInsByClass(SymbOpSub.class), new ChocoSub());
		map.put(SymbOperator.getInsByClass(SymbOpMult.class), new ChocoMult());
		map.put(SymbOperator.getInsByClass(SymbOpDiv.class), new ChocoDiv());
		map.put(SymbOperator.getInsByClass(SymbCompEqual.class), new ChocoEqual());
		map.put(SymbOperator.getInsByClass(SymbCompNotEqual.class), new ChocoNotEqual());
		map.put(SymbOperator.getInsByClass(SymbCompLess.class), new ChocoLess());
		map.put(SymbOperator.getInsByClass(SymbCompLessEqual.class), new ChocoLessEqual());
		map.put(SymbOperator.getInsByClass(SymbCompGreater.class), new ChocoGreater());
		map.put(SymbOperator.getInsByClass(SymbCompGreaterEqual.class), new ChocoGreaterEqual());
		map.put(SymbOperator.getInsByClass(SymbOpLogicOr.class), new ChocoLogicOr());
		map.put(SymbOperator.getInsByClass(SymbOpLogicAnd.class), new ChocoLogicAnd());
		map.put(SymbOperator.getInsByClass(SymbOpLogicNot.class), new ChocoLogicNot());
	}
}
