/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Apr 9, 2006
 */
package vpc.core.decl;

import cck.parser.AbstractToken;
import vpc.core.base.Function;
import vpc.core.types.*;
import vpc.tir.expr.Operator;
import vpc.util.*;

import java.util.Map;

/**
 * The <code>Method</code> class represents the VPC compiler's view of a
 * method. A method consists of a method contents, its return type, and the
 * types of its arguments. The two inner classes, <code>Virtual</code> and
 * <code>Static</code> represent the two different types of methods that
 * can be present in Virgil programs.
 * Methods might have several different code representations throughout
 * the compilation process. The functionality associated with a method
 * representation is encapsulated in the <code>MethodRep</code> class.
 *
 * @author Ben L. Titzer
 */
public class Method extends Member {
    public Family family;

    protected Map<String, MethodRep> codereps;

    protected final TypeParam[] typeParams;
    protected final TypeRef returnType;
    protected final TypeRef[] argumentTypes;

    public Method(CompoundDecl decl, AbstractToken tok, TypeRef tnf, TypeParam[] tp, boolean p) {
        super(decl, tok, tnf, p);
        returnType = Function.getReturnType(tnf);
        argumentTypes = Function.getParameterTypes(tnf);
        codereps = Ovid.newMap();
        typeParams = tp;
    }

    public MethodRep getMethodRep(String name) {
        return codereps.get(name);
    }

    public TypeParam[] getTypeParams() {
        return typeParams;
    }

    public void addMethodRep(String name, MethodRep rep) {
        codereps.put(name, rep);
    }

    public String getFullName() {
        return container.getName() + ":" + name + "()";
    }

    public TypeRef[] getParameterTypes() {
        return argumentTypes;
    }

    public TypeRef getReturnType() {
        return returnType;
    }

    public boolean isRootOfFamily() {
        return family != null && family.rootMethod == this;
    }

    public String toString() {
        return Function.buildCallableName("method "+name,
                argumentTypes,
                returnType);
    }

    public interface MethodRep {
    }

    public static class BaseMethodRep<Rep, T extends Type> implements MethodRep {

        protected final HashList<String, Temporary<T>> temps;
        protected final HashList<String, Temporary<T>> params;
        protected Rep body;
        protected int numTemps;

        public BaseMethodRep() {
            temps = new HashList<String, Temporary<T>>();
            params = new HashList<String, Temporary<T>>();
        }

        public void setBody(Rep b) {
            body = b;
        }

        public Rep getBody() {
            return body;
        }

        public Temporary<T> newTemporary(T t) {
            return newVariable("__t" + numTemps, t);
        }

        public Temporary<T> newVariable(String name, T t) {
            String unique = uniquify(name);
            Temporary<T> tmp = new Temporary<T>(unique, t, numTemps++);
            temps.add(unique, tmp);
            return tmp;
        }

        private String uniquify(String name) {
            String uniq = name;
            for ( int cntr = 0; temps.get(uniq) != null; cntr++) {
                uniq = name + cntr;
            }
            return uniq;
        }

        public Temporary<T> newParam(String name, T t) {
            Temporary<T> tmp = new Temporary<T>(name, t, numTemps++);
            params.add(name, tmp);
            return tmp;
        }

        public int getNumberOfTemps() {
            return numTemps;
        }

        public Iterable<Temporary<T>> getTemps() {
            return temps;
        }

        public Iterable<Temporary<T>> getParams() {
            return params;
        }

    }

    /**
     * The <code>Temporary</code> inner class represents a temporary variable
     * used within a method in a Virgil program. Temporaries each have an
     * method-unique identifier and a type associated with them. The
     * <code>Temporary</code> class also stores a String contents that is
     * the source-level variable that corresponds to this temporary.
     */
    public static class Temporary<T extends Type> implements Variable {

        protected final String name;
        public final int id;
        protected final T type;

        Temporary(String nm, T t, int i) {
            name = nm;
            type = t;
            id = i;
        }

        public T getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public String toString() {
            return name;
        }

        public boolean equals(Object o) {
            return this == o;
        }

        public int hashCode() {
            return name.hashCode();
        }
    }

    /**
     * The <code>Method.Family</code> class represents a method family, a set of
     * methods that are invocable through virtual dispatch.
     */
    public static class Family {
        public final Method rootMethod;
        public Field metaField;
        protected Hierarchy<Method> methods;

        public Family(Method root) {
            rootMethod = root;
            methods = new Hierarchy<Method>();
            methods.addRoot(root);
        }

        public void addMethod(Method p, Method m) {
            methods.add(p, m);
        }

    }

    public static class UnimplementedException extends Operator.Exception {
        public UnimplementedException() {
            super("method not implemented");
        }
    }
}
