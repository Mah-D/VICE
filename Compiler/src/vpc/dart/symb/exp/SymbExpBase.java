package vpc.dart.symb.exp;


public abstract class SymbExpBase {
	public abstract SymbExpProperty getExpProp();
	public SymbExpBase findSubExp(Class<? extends SymbExpBase> target) {
		if(getClass().equals(target))
			return this;
		else
			return null;
	}
	public abstract Object toExpObject(SymbExpGeneratorI gen);
}
