package vpc.dart.symb.exp;

import vpc.dart.symb.operator.SymbOperator;

public class SymbExpOp extends SymbExpBase {
	public SymbOperator operator;
	public SymbExpBase[] operands;
	
	public String toString() {
		String ret = "(" + operator.getName();
		for(SymbExpBase exp : operands)
			ret += "," + exp.toString();
		return ret + ")";
	}
	public SymbExpOp(SymbOperator op, SymbExpBase ...oprs) {
		operator = op;
		operands = oprs;
	}

	public SymbExpProperty getExpProp() {
		for(SymbExpBase subExp : operands) {
			if(subExp.getExpProp() == SymbExpProperty.NonLinear)
				return SymbExpProperty.NonLinear;
		}
		return SymbExpProperty.Linear;
	}
	public SymbExpBase findSubExp(Class<? extends SymbExpBase> target) {
		if(getClass().equals(target)) {
			return this;
		} else {
			for(SymbExpBase exp : operands) {
				SymbExpBase tar = exp.findSubExp(target);
				if(tar != null)
					return tar;
			}
		}
		return null;
	}
	public Object toExpObject(SymbExpGeneratorI gen) {
		return gen.generate(this);
	}
}
