package vpc.dart;


import cck.util.Util;
import vpc.core.Program;
import vpc.core.Value;
import vpc.core.base.PrimBool;
import vpc.core.base.PrimVoid;
import vpc.core.virgil.VirgilComponent;
import vpc.dart.exception.PathNotConsistent;
import vpc.dart.selector.InstrumentSelectorI;
import vpc.dart.selector.SimpleIntegerSelector;
import vpc.dart.symb.operator.*;
import vpc.dart.symb.operator.compare.*;
import vpc.dart.symc.SCValue;
import vpc.tir.TIRBlock;
import vpc.tir.TIRConst;
import vpc.tir.TIRExpr;
import vpc.tir.TIRIfExpr;
import vpc.tir.TIRInterpreter;
import vpc.tir.TIRLocal;
import vpc.tir.TIRSwitch;
import vpc.tir.TIRInterpreter.Frame;
import vpc.tir.expr.Operator;

public class DartInterpreter extends TIRInterpreter {
	
	private PathChecker m_checker;
	private ExternalVariable m_extVariables;
	private InstrumentSelectorI m_selector = new SimpleIntegerSelector();
	
	protected void loadAllSymbolicOperators() {
		(new SymbOpSub()).register();
		(new SymbOpAdd()).register();
		(new SymbOpMult()).register();
		(new SymbOpDiv()).register();
		(new SymbOpNeg()).register();
		(new SymbCompEqual()).register();
		(new SymbCompGreater()).register();
		(new SymbCompGreaterEqual()).register();
		(new SymbCompLess()).register();
		(new SymbCompLessEqual()).register();
		(new SymbCompNotEqual()).register();
		(new SymbOpLogicAnd()).register();
		(new SymbOpLogicOr()).register();
		(new SymbOpLogicNot()).register();
		(new SymbOpCast()).register();
	}
	
	public DartInterpreter(Program p, PathChecker checker, ExternalVariable extV) {
		super(p);
		m_checker = checker;
		m_extVariables = extV;
		loadAllSymbolicOperators();
	}
    public Value visit(TIRBlock st, Frame frame) {
    	try {
	   		m_checker.enterBlock();
	   		Value ret = super.visit(st, frame);
	   		return ret;
    	} finally {
    		m_checker.exitBlock();
    	}
    }
    
    
    public Value visit(TIRConst.Value c, Frame frame) {
        return new SCValue(super.visit(c, frame), true);
    }
	
    public Value apply1(Operator.Op1 operator, TIRExpr[] operands) throws Operator.Exception {
//		if(!m_selector.isInsteresting(operator))
//			return super.apply1(operator, operands);
        assert operands.length == 1;
        SCValue v0 = SCValue.getSCValue(eval(operands[0]));
        Value concrete = null;
        try {
            concrete = operator.apply1(v0.getConcreteValue());
        } catch (VirgilComponent.InitCheckException e) {
            // retry operator (once only) if failure due to init dependency
            initComponent(e.component);
            concrete = operator.apply1(v0.getConcreteValue());
        }
        return applySymColic(operator, concrete, v0);
	}
	private SCValue applySymColic(Operator op, Value concreteValue, SCValue ...args) {
		SymbOperator sOp = SymbOperator.getOperator(op.getClass());
		// if the symbolic operator can be found, replace the value with concrete value.
		if(sOp == null)
			return new SCValue(concreteValue, false);
		else
			return new SCValue(concreteValue, sOp.computeSymbolic(concreteValue, SCValue.getSymbolicValues(args)));
	}
	
	private SCValue getSwitchSymColic(boolean cond, SCValue left, SCValue right) {
		return new SCValue(PrimBool.toValue(cond),
				SymbOperator.getInsByClass(SymbCompEqual.class)
				.computeSymbolic(PrimBool.toValue(cond), left.getSymbolicValue(), right.getSymbolicValue()));
	}
	
	public Value apply2(Operator.Op2 operator, TIRExpr[] operands) throws Operator.Exception {
        assert operands.length == 2;
        SCValue v0 = SCValue.getSCValue(eval(operands[0]));
        SCValue v1 = SCValue.getSCValue(eval(operands[1]));
        
        Value concrete = null;
        try {
            concrete = operator.apply2(v0.getConcreteValue(), v1.getConcreteValue());
            
        } catch (VirgilComponent.InitCheckException e) {
            // retry operator (once only) if failure due to init dependency
            initComponent(e.component);
            concrete = operator.apply2(v0.getConcreteValue(), v1.getConcreteValue());
        }
        
		if(m_selector.isInsteresting(operator))
			return applySymColic(operator, concrete, v0, v1);
		else
			return concrete;
	}
    public Value visit(TIRLocal.Get l, Frame frame) {
        Value v = super.visit(l, frame);
        /*
        if(v == null)
        	v = super.visit(l, frame); //throw new Util.Error("variable be used without initilization");
        */
        return v;
        
    }	
	public Value apply3(Operator.Op3 operator, TIRExpr[] operands) throws Operator.Exception {
	    assert operands.length == 3;
	    SCValue v0 = SCValue.getSCValue(eval(operands[0]));
	    SCValue v1 = SCValue.getSCValue(eval(operands[1]));
	    SCValue v2 = SCValue.getSCValue(eval(operands[2]));
	    Value concrete = null;
	    try {
	    	concrete =  operator.apply3(v0.getConcreteValue(), v1.getConcreteValue(), v2.getConcreteValue());
	    } catch (VirgilComponent.InitCheckException e) {
	        // retry operator (once only) if failure due to init dependency
	        initComponent(e.component);
	        concrete = operator.apply3(v0.getConcreteValue(), v1.getConcreteValue(), v2.getConcreteValue());
	    }
	   
		if(m_selector.isInsteresting(operator))
			return applySymColic(operator, concrete, v0, v1, v2);
		else
			return concrete;
	}
	
	public Value applyN(Operator operator, TIRExpr[] operands) throws Operator.Exception {
	    SCValue[] vals = SCValue.getSCValues(eval(operands));
	    Value concrete = null;
	    try {
	        concrete = operator.apply(env, SCValue.getConcreteValues(vals));
	    } catch (VirgilComponent.InitCheckException e) {
	        // retry operator (once only) if failure due to init dependency
	        initComponent(e.component);
	        concrete = operator.apply(env, vals);
	    }
		if(m_selector.isInsteresting(operator))
			return applySymColic(operator, concrete, vals);
		else
			return concrete;
	}
    public Value visit(TIRIfExpr s, Frame frame) {
        SCValue val = SCValue.getSCValue(eval(s.condition));
        if(!m_checker.checkAndStep(val)) {
        	throw new PathNotConsistent();
        }
        if (PrimBool.fromValue(val.getConcreteValue())) return eval(s.true_target);
        else return eval(s.false_target);
    }
    public Value visit(TIRSwitch s, Frame frame) {
        boolean eval = false;
        SCValue val = SCValue.getSCValue(eval(s.expr));
        // linear search for now; could be hashtable or array
        for (TIRSwitch.Case c : s.cases) {
            for (TIRConst v : c.value) {
            	SCValue caseVal = SCValue.getSCValue(eval(v));
                if (Value.compareValues(val.getConcreteValue(), caseVal.getConcreteValue())) {
                	if(!m_checker.checkAndStep(getSwitchSymColic(true, val, caseVal)))
                		throw new PathNotConsistent();
                    eval(c.body);
                    eval = true;
                } else if(!m_checker.checkAndStep(getSwitchSymColic(false,  val, caseVal)))
                	throw new PathNotConsistent();
            }
        }
        // no match; execute default case
        if (!eval && s.defcase != null) eval(s.defcase.body);
        return PrimVoid.VALUE;
    }
//    public Value visit(TIRConst.Symbol c, Frame frame) {
//        return new SCValue(fromJavaString(program, c.value()), true);
//    }    

}
