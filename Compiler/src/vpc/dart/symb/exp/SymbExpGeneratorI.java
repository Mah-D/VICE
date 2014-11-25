package vpc.dart.symb.exp;

public interface SymbExpGeneratorI {
	Object generate(SymbExpOp exp);
	Object generate(SymbExpVariable exp);
	Object generate(SymbExpConst exp);
}
