/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Jan 26, 2006
 */

package vpc.vst.tir;

import vpc.core.decl.*;
import vpc.core.types.Type;
import vpc.tir.*;
import vpc.util.Ovid;
import vpc.vst.VSTUtil;
import vpc.vst.tree.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author Ben L. Titzer
 */
public abstract class BuilderEnv {
    protected TIRRep method;
    protected HashMap<VSTVarDecl, Method.Temporary> varMap;
    protected TIRBuilder tirBuilder;
    protected CompoundDecl compound;
    protected Method.Temporary thisParam;

    protected BuilderEnv(TIRBuilder tirBuilder, CompoundDecl comp) {
        this.tirBuilder = tirBuilder;
        compound = comp;
    }

    abstract TIROperator getField(Field field);

    abstract VSTSuperClause getSuperClause(VSTConstructorDecl cd);

    abstract TIRExpr newThisRef();

    TIRRep buildMethod(VSTMethodDecl md) {
        return buildCodeRep(md.params, md.block);
    }

    TIRRep buildConstructor(VSTConstructorDecl cd, Iterable<Field> fields) {
        method = new TIRRep();
        varMap = Ovid.newHashMap();
        // add parameters to the constructor
        expandParams(cd == null ? TIRBuilder.NOPARAMS : cd.params, compound.getCanonicalType());

        VSTCompiler bb = new VSTCompiler(this);
        TIRBlock block = newBlock("L");
        addValueInits(cd, block, bb);
        addSuperClause(cd, block, bb);
        addFieldInits(fields, block, bb);
        addConstructorBody(cd, block, bb);
        method.setBody(block);
        return method;
    }

    public TIRBlock newBlock(String lbl) {
        return new TIRBlock(method.newLabel(lbl));
    }

    public TIRBlock newBlock(String lbl, TIRExpr... list) {
        TIRBlock block = new TIRBlock(method.newLabel(lbl));
        for ( TIRExpr e : list ) block.addExpr(e);
        return block;
    }

    private void addSuperClause(VSTConstructorDecl cd, TIRBlock block, VSTCompiler bb) {
        VSTSuperClause sup = getSuperClause(cd);
        if (sup != null) block.addExpr(bb.visit(sup));
    }

    private void addConstructorBody(VSTConstructorDecl cd, TIRBlock block, VSTCompiler bb) {
        if (cd != null && cd.block != null) {
            for (VSTStmt s : cd.block.stmts) block.addExpr(s.accept(bb));
        }
    }

    private void addValueInits(VSTConstructorDecl cd, TIRBlock block, VSTCompiler bb) {
        if (cd == null) return;
        for (VSTParamDecl pd : cd.params) {
            Field f = compound.getLocalField(pd.getName());
            VSTFieldDecl fd = VSTUtil.vstRepOf(f);
            if (fd != null && fd.isValue) {
                TIROperator r = getField(f);
                block.addExpr(bb.convertGet(pd.getToken(), r, TIRUtil.$GET(varMap.get(pd))));
            }
        }
    }

    private void addFieldInits(Iterable<Field> fields, TIRBlock block, VSTCompiler bb) {
        for (Field f : fields) {
            VSTFieldDecl fd = VSTUtil.vstRepOf(f);
            if (fd.init != null) {
                TIROperator r = getField(f);
                // TODO: this source point location should be the assignment token
                block.addExpr(bb.convertGet(fd.getToken(), r, bb.buildExpr(fd.init)));
            }
        }
    }

    private TIRRep buildCodeRep(List<VSTParamDecl> params, VSTBlock block) {
        method = new TIRRep();
        varMap = Ovid.newHashMap();
        // add parameters to the method.
        expandParams(params, compound.getCanonicalType());
        buildBody(block);
        return method;
    }

    private void expandParams(List<VSTParamDecl> params, Type thisType) {
        thisParam = method.newParam(TIRUtil.THIS_PARAM, thisType);
        for (VSTParamDecl p : params) {
            Type type = p.getType();
            Method.Temporary t = method.newParam(p.getName(), type);
            varMap.put(p, t);
        }
    }

    private void buildBody(VSTBlock body) {
        // build the main block of the method.
        VSTCompiler bb = new VSTCompiler(this);
        if ( body != null ) {
            TIRExpr nbody = bb.buildStmtBlock(body.stmts);
            method.setBody(nbody);
            nbody.setSourcePoint(body.getToken().getSourcePoint());
        } else {
            method.setBody(new TIRThrow(Method.UnimplementedException.class, null));
        }
    }

    protected Method.Temporary newVariable(VSTVarDecl v) {
        Method.Temporary<Type> t = method.newVariable(v.getName(), v.getType());
        varMap.put(v, t);
        return t;
    }
} // end of BuilderEnv
