/**
 * Copyright (c) 2007, Ben L. Titzer
 * See the file "license.txt" for details.
 *
 * Created Jan 27, 2008
 */
package vpc.core.virgil;

import vpc.core.base.*;
import vpc.core.types.Type;
import vpc.core.types.Capability;

/**
 * The <code>V3TypeSystem</code> definition.
 *
 * @author Ben L. Titzer
 */
public class V3TypeSystem extends V2TypeSystem {

    protected boolean isSubtypeFunction(Type src, Type target) {
        if (isNull(src)) return true;
        if (!isFunction(src)) return false;
        Function.IType fsrc = (Function.IType)src;
        Function.IType ftarget = (Function.IType)target;
        Type[] srcParamTypes = Tuple.getParameterTypes(fsrc);
        Type[] targetParamTypes = Tuple.getParameterTypes(ftarget);
        if (srcParamTypes.length != targetParamTypes.length) return false;
        for (int i = 0; i < srcParamTypes.length; i++) {
            // functions are contra-variant in the parameter types
            if (!isAssignable(targetParamTypes[i], srcParamTypes[i])) return false;
        }
        // functions are co-variant in the return type
        return isAssignable(fsrc.getResultType(), ftarget.getResultType());
    }

    protected boolean isSubtypeTuple(Type src, Type target) {
        if (!isTuple(target)) return false;
        Tuple.IType fsrc = (Tuple.IType)src;
        Tuple.IType ftarget = (Tuple.IType)target;
        Type[] srcTypes = fsrc.elements();
        Type[] targetTypes = ftarget.elements();
        for (int i = 0; i < srcTypes.length; i++) {
            // tuples are co-variant in the parameter type
            if (!isAssignable(srcTypes[i], targetTypes[i])) return false;
        }
        return true;
    }

    protected void registerOps() {
        registerBinOp(PrimBool.TYPE, new Capability.BinOp("and", new PrimBool.AND()));
        registerBinOp(PrimBool.TYPE, new Capability.BinOp("or", new PrimBool.OR()));
        registerUnaryOp(PrimBool.TYPE, new Capability.UnaryOp("!", new PrimBool.NOT()));

        // register the operators for characters
        registerBinOp(PrimChar.TYPE, new Capability.BinOp("<", new PrimChar.LT()));
        registerBinOp(PrimChar.TYPE, new Capability.BinOp(">", new PrimChar.GR()));
        registerBinOp(PrimChar.TYPE, new Capability.BinOp("<=", new PrimChar.LTEQ()));
        registerBinOp(PrimChar.TYPE, new Capability.BinOp(">=", new PrimChar.GREQ()));

        registerBinOp(PrimInt32.TYPE, new Capability.BinOp("+", new PrimInt32.ADD()));
        registerBinOp(PrimInt32.TYPE, new Capability.BinOp("-", new PrimInt32.SUB()));
        registerBinOp(PrimInt32.TYPE, new Capability.BinOp("*", new PrimInt32.MUL()));
        registerBinOp(PrimInt32.TYPE, new Capability.BinOp("/", new PrimInt32.DIV()));
        registerBinOp(PrimInt32.TYPE, new Capability.BinOp("%", new PrimInt32.MOD()));
        // V3 adds the ability to perform bitwise operations in integers (and removes raw types)
        registerBinOp(PrimInt32.TYPE, new Capability.BinOp("&", new PrimInt32.AND()));
        registerBinOp(PrimInt32.TYPE, new Capability.BinOp("|", new PrimInt32.OR()));
        registerBinOp(PrimInt32.TYPE, new Capability.BinOp("^", new PrimInt32.XOR()));
        registerBinOp(PrimInt32.TYPE, new Capability.BinOp("#<<", new PrimInt32.SHL()));
        registerBinOp(PrimInt32.TYPE, new Capability.BinOp("#>>", new PrimInt32.SHR()));

        // add the relational operators
        registerBinOp(PrimInt32.TYPE, new Capability.BinOp("<", new PrimInt32.LT()));
        registerBinOp(PrimInt32.TYPE, new Capability.BinOp(">", new PrimInt32.GR()));
        registerBinOp(PrimInt32.TYPE, new Capability.BinOp("<=", new PrimInt32.LTEQ()));
        registerBinOp(PrimInt32.TYPE, new Capability.BinOp(">=", new PrimInt32.GREQ()));

    }

    protected Capability.BinOp resolveRawOp(String op, Type t1, Type t2) {
        // V3 does not support raw types
        return null;
    }

}
