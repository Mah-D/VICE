package vpc.dart.symc;

import vpc.core.Value;
import vpc.dart.exception.DartNotImplemented;
import vpc.dart.symb.exp.SymbExpBase;

class SymbolicConcreteSolver {
	public static SymbExpBase solve(SymbExpBase originalExp, Value concreteValue) {
		if(originalExp == null) {
			throw new DartNotImplemented("symbolic value is null");
//			return new SymbExpConst(concreteValue, false);
		}
		return originalExp;
	}
}
