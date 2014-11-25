/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Nov 27, 2006
 */

package vpc.core.virgil;

import cck.help.HelpCategory;
import cck.parser.SourceError;
import cck.parser.SourcePoint;
import cck.text.StringUtil;
import vpc.core.Program;
import vpc.util.Ovid;
import vpc.vst.tree.VSTNode;

import java.util.List;
import java.util.Properties;

/**
 * The <code>VirgilError</code> class encapsulates source error instances
 * related to the typechecking and well-formedness checks performed by
 * the Virgil compiler during semantic analysis.
 *
 * @author Ben L. Titzer
 */
public class VirgilError {

    public static final List<ErrorGen> allErrors = Ovid.newLinkedList();
    private static final Properties props = new Properties();

    /**
     * The <code>VirgilError.Builder</code> implements an object that can generate
     * source errors of a particular kind.
     */
    public static class ErrorGen extends HelpCategory {
        protected final String name;
        protected final String message;

        public ErrorGen(String n, String msg, String h) {
            super(n, h);
            name = n;
            message = msg;
            allErrors.add(this);
        }

        public SourceError gen(VSTNode n, String... p) {
            return gen(n.getToken().getSourcePoint(), p);
        }

        public SourceError gen(SourcePoint pt, String... p) {
            // render the message
            String msg = StringUtil.stringReplace(message, props, p);
            // construct a new source error
            return new SourceError(name, pt, msg, p);
        }
    }

    public static final ErrorGen ArgumentCountMismatch = new ErrorGen("ArgumentCountMismatch",
            "argument count mismatch, expected: $1 and found $2",
            "Generated when the typechecker analyzes function application expressions " +
            "(method calls) in the program. The VLS states that the number of arguments " +
            "to the function must match the signature of the function.");

    public static final ErrorGen ArgumentCountMismatchInNew = new ErrorGen("ArgumentCountMismatchInNew",
            "argument count mismatch in new, expected: $1 and found $2",
            "Generated when the typechecker analyzes object allocations in the program. The VLS " +
            "states that the number of arguments to the constructor must match the signature " +
            "of the constructor.");

    public static final ErrorGen BuiltinRedefined = new ErrorGen("BuiltinRedefined",
            "type %1 is already defined as a built-in",
            "Generated when the program attempts to redefine a type that is already " +
            "defined by the Virgil language specification. Some types are considered " +
            "\"built-in\" to the language, and cannot be redefined or overridden by " +
            "the program.");

    public static final ErrorGen CannotAssignToMember = new ErrorGen("CannotAssignToMember",
            "cannot assign to member",
            "Generated when an assignment to a method member of a class or component is " +
            "encountered in the program. Methods are not updatable in Virgil; the " +
             "VLS states that any attempt to assign to a method of an object or " +
            "component is an error.");

    public static final ErrorGen CannotCompareValues = new ErrorGen("CannotCompareValues",
            "cannot compare values of type %1 and %2",
            "Generated when the typechecker analyzes comparisons in the program and the types of " +
            "the two expressions are not directly comparable. The VLS defines the rules " +
            "for comparable types in more detail.");

    public static final ErrorGen CannotInferEmptyArrayType = new ErrorGen("CannotInferEmptyArrayType",
            "cannot infer type of empty array initializer",
            "Generated when the typechecker encounters a declaration that lacks an explicit type, " +
            "but the initialization expression is an empty array. The VLS explains that the " +
            "compiler cannot infer a specific type for the variable, and thus must generate " +
            "an error.");

    public static final ErrorGen CannotInferTypeParam = new ErrorGen("CannotInferTypeParam",
            "cannot infer type parameter %1",
            "Generated when the typechecker encounters a use of a parameterized method where " +
            "the compiler cannot infer the type parameters from the usage site. The VLS defines " +
            "the rules that the compiler uses to infer type parameters and what circumstances " +
            "constitute an error.");

    public static final ErrorGen CannotOverrideArity = new ErrorGen("CannotOverrideArity",
            "cannot override parameter type of method %1",
            "Generated when the declaration of a class method that overrides a parent class's " +
            "method attempts to change the number of parameters to the method. The VLS " +
            "specification states " +
            "that the number and types of parameters to a method must exactly match those " +
            "of the overridden method in the parent class.");

    public static final ErrorGen CannotOverrideParamType = new ErrorGen("CannotOverrideParamType",
            "cannot override parameter type of method %1",
            "Generated when the declaration of a class method that overrides a parent class's " +
            "method attempts to override the type of a parameter. Virgil does not support " +
            "contravariant parameter types on overriden methods. The VLS specification states " +
            "that the number and types of parameters to a method must exactly match those " +
            "of the overridden method in the parent class.");

    public static final ErrorGen CannotOverrideTypeParams = new ErrorGen("CannotOverrideTypeParams",
            "cannot type parameters of method %1",
            "Generated when the declaration of a class method that overrides a parent class's " +
            "method attempts to override the type parameters.  The VLS specification states that " +
            "the type parameters for a method must have the same number and position as those of the " +
            "overridden method in the parent class.");

    public static final ErrorGen CannotOverrideReturnType = new ErrorGen("CannotOverrideReturnType",
            "cannot override return type of method %1",
            "Generated when the declaration of a class method that overrides a parent class's " +
            "method attempts to override the return type. Virgil does not support covariant " +
            "return types on overriden methods. The VLS specification states that " +
            "the return type for a method must exactly match the return type of the " +
            "overridden method in the parent class.");

    public static final ErrorGen CannotResolveEntrypoint = new ErrorGen("CannotResolveEntrypoint",
            "cannot resolve required entrypoint %1",
            "Generated when the linkage model attempts to resolve an entrypoint needed for " +
            "correct generation of code on the target platform.");

    public static final ErrorGen CannotUnifyBranchTypes = new ErrorGen("CannotUnifyBranchTypes",
            "cannot unify branch types: %1 and %2",
            "Generated when the typecheck attempts to infer the type of a conditional expression " +
            "but the types of the branches cannot be unified. The VLS defines the " +
            "unification rules for types.");

    public static final ErrorGen CannotUnifyElemTypes = new ErrorGen("CannotUnifyElemTypes",
            "cannot unify element types in array: %1 and %2",
            "Generated when the typechecker attempts to infer the type of an array initializer " +
            "but the element types of the array cannot be unified. The VLS defines the " +
            "unification rules for types.");

    public static final ErrorGen ConstructorRedefined = new ErrorGen("ConstructorRedefined",
            "constructor is already defined at %1",
            "Generated when the program attempts to redefine the constructor for a class " +
            "or component. The VLS specification states that each class and component " +
            "must declare at most one constructor.");

    public static final ErrorGen CyclicInheritance = new ErrorGen("CyclicInheritance",
            "cyclic inheritance detected",
            "Generated when the compiler checks the consistency of the class hierarchy and " +
            "detects a cycle among the inheritance relation of a set of classes. The VLS " +
            "states that each class may extend at most one parent class, and that no " +
            "cycles may exist among the classes.");

    public static final ErrorGen DefaultCaseRedefined = new ErrorGen("DefaultCaseRedefined",
            "default case already defined at: %1",
            "Generated when the typechecker analyzes a switch statement and the program " +
            "attempts to define the default case more than once.");

    public static final ErrorGen DuplicateCase = new ErrorGen("DuplicateCase",
            "duplicate case, previous: %1",
            "Generated when the typechecker analyzes a switch statement and the program " +
            "attempts to define a duplicate switch case. ");

    public static final ErrorGen ExpectedExprType = new ErrorGen("ExpectedExprType",
            "expression type expected, found: %1",
            "Generated when the typechecker encounters an identifier that is used an expression, " +
            "but the identifier refers to a program quantity that does not have a type, " +
            "such as a component.");

    public static final ErrorGen ExpectedFunction = new ErrorGen("ExpectedFunction",
            "function expected, found: %1",
            "Generated when the typechecker analyzes function application expressions " +
            "(method calls) in the program. The VLS states that the receiver expression " +
            "of an application must have a function type.");

    public static final ErrorGen ExpectedIndexable = new ErrorGen("ExpectedIndexable",
            "indexable type expected, found: %1",
            "Generated when the typechecker analyzes index expressions (e.g. array access). " +
            "The VLS states that the receiver object or value must be an indexable type.");

    public static final ErrorGen ExpectedObjectType = new ErrorGen("ExpectedObjectType",
            "object type expected, found: %1",
            "Generated when the typechecker encounters a construct in the program that requires " +
            "an object type, but the program uses an expression or type literal that is " +
            "not an object type. The VLS defines what constructs require an object type.");

    public static final ErrorGen ExpectedReturnType = new ErrorGen("ExpectedReturnType",
            "return type expected, found: %1",
            "Generated when the typechecker analyzes method declarations in the program " +
            "and encounters a declared return type that is not valid. ");

    public static final ErrorGen ExpectedVarType = new ErrorGen("ExpectedVarType",
            "variable type expected, found: %1",
            "Generated when the typechecker analyzes declarations of fields and local " +
            "variables in the program. The VLS defines the valid types that a " +
            "declared field or local may have; variable declarations with a " +
            "type that is not within this class of types cause an error.");

    public static final ErrorGen ExprCannotBeVoid = new ErrorGen("ExprCannotBeVoid",
            "expression cannot be of type void",
            "Generated when the typechecker detects that an expression has type void but occurs in " +
            "a situation where a non-void type is expected (e.g. an assignment).");

    public static final ErrorGen InvalidLiteral = new ErrorGen("InvalidLiteral",
            "invalid literal",
            "Generated for a boolean, integer, character, or string literal that is not " +
            "legal according to the language specification. This error may be generated if " +
            "the literal is too large, out of range, or contain invalid characters.");

    public static final ErrorGen InvalidConstructorParam = new ErrorGen("InvalidConstructorParam",
            "invalid constructor parameter, %1",
            "Generated for a boolean, integer, character, or string literal that is not " +
            "legal according to the language specification. This error may be generated if " +
            "the literal is too large, out of range, or contain invalid characters.");

    public static final ErrorGen InvalidModifier = new ErrorGen("InvalidModifier",
            "invalid modifier: %1",
            "Generated when the program attempts to use an invalid access modifier on the " +
            "declaration of a class or component member. The VLS defines the valid modifiers " +
            "for each kind of declaration.");

    public static final ErrorGen InvalidTypeCast = new ErrorGen("InvalidTypeCast",
            "invalid type cast, $1",
            "Generated when the typechecker encounters a type cast expression in the program " +
            "that is not legal according to the VLS specification.");

    public static final ErrorGen InvalidTypeQuery = new ErrorGen("InvalidTypeQuery",
            "invalid type query, $1",
            "Generated when the typechecker encounters a type query expression in the program " +
            "that is not legal according to the VLS specification.");

    public static final ErrorGen LocalMustHaveTypeOrInit = new ErrorGen("LocalMustHaveTypeOrInit",
            "variable %1 must have type or initializer",
            "Generated when the declaration of a local variable does not include either " +
            "a type or an initializing expression from which the type can be inferred.");

    public static final ErrorGen MemberDefinedInSuper = new ErrorGen("MemberDefinedInSuper",
            "member %1 is already defined in superclass",
            "Generated when the declaration of a member of a class has the same name as " +
            "a member inherited from a superclass. The VLS states that each member " +
            "of a class must be given a unique name that does not conflict with " +
            "members inherited from the super class. Virgil does not support field " +
            "hiding.");

    public static final ErrorGen MemberRedefined = new ErrorGen("MemberRedefined",
            "member %1 is already defined at %2",
            "Generated when the declaration of a class or component contains a declaration " +
            "of a member (either a field or a method) that conflicts with a previously " +
            "declared member. The VLS states that each member with in each class or " +
            "component must have a unique name that is a valid Virgil identifier. Virgil " +
            "does not support method overloading by overloading parameter types.");

    public static final ErrorGen MissingReturn = new ErrorGen("MissingReturn",
            "missing return statement in non-void method",
            "Generated when the body of a method that has been declared to return a non-void " +
            "type does not include a return statement. The VLS states that each method that is " +
            "declared to return a non-void type must contain at least one return statement " +
            "at the flow end(s) of the method.");


    public static final ErrorGen NoDefaultConstructor = new ErrorGen("NoDefaultConstructor",
            "no default constructor available in super class",
            "Generated when a class declaration omits a constructor or when a constructor " +
            "declaration omits an explicit call to the parent class's constructor, but " +
            "no default constructor is defined in the parent class. The VLS states that " +
            "if the parent class has a default (i.e. no arguments) constructor, then the " +
            "compiler will insert an implicit call to the parent constructor; otherwise " +
            "the child class must include an explicit call to the appropriate constructor.");

    public static final ErrorGen NonSwitchableType = new ErrorGen("NonSwitchableType",
            "cannot switch on value of type %1",
            "Generated when the typechecker analyzes a switch statement and the type of the " +
            "value is not switchable. The VLS defines what types are valid for use in switch " +
            "statements.");

    public static final ErrorGen NonVoidConstructorReturn = new ErrorGen("NonVoidConstructorReturn",
            "cannot return value from constructor",
            "Generated when the body of a constructor contains a return statement includes a " +
            "value. The VLS states that any return statements that occur within the body of " +
            "a constructor must not return a value.");

    public static final ErrorGen NonVoidReturn = new ErrorGen("NonVoidReturn",
            "cannot return value from void method",
            "Generated when the body of a method that has been declared to return void (i.e. " +
            "without a return type) contains a return statement includes a value. The " +
            "VLS states that any and all return statements that occur within the body of " +
            "a void method must not return a value.");

    public static final ErrorGen NoSuperClass = new ErrorGen("NoSuperClass",
            "class %1 has no super class",
            "Generated when a constructor declaration for a class attempts to call the " +
            "super constructor, but the class does not have a parent class. The VLS " +
            "states that a constructor can include a call to its parent class's " +
            "constructor only if there is a parent class, and if the number and types " +
            "of parameters to the parent constructor match.");

    public static final ErrorGen NotAnEqualityType = new ErrorGen("NotAnEqualityType",
            "equality type expected, found: %1",
            "Generated when the typechecker analyzes comparisons in the program. The VLS states that " +
            "the operands of the equality operator must both be equality types.");

    public static final ErrorGen NotAnLvalue = new ErrorGen("NotAnLvalue",
            "expression is not assignable",
            "Generated when an expression is used as the target of an assignment statement " +
            "or implicit assignment expression, but the expression does not correspond " +
            "to a valid location in the program. The VLS defines what expressions constitute " +
            "valid locations that may be used as the target of an assignment.");

    public static final ErrorGen NotAStatement = new ErrorGen("NotAStatement",
            "not a statement",
            "Generated during parsing when the compiler is expecting a statement, but parses " +
            "only an expression. The VLS defines what syntactic forms a statement may take.");

    public static final ErrorGen NotComputable = new ErrorGen("NotComputable",
            "value is not immediately computable",
            "Generated when the typechecker analyzes a case of a switch statement and the value " +
            "of the case is not immediately computable. The VLS defines the conditions under " +
            "which a constant value for a case statement is immediately commutable.");

    public static final ErrorGen ParameterRedefined = new ErrorGen("ParameterRedefined",
            "parameter %1 is already defined",
            "Generated when the declaration of a method or constructor attempts to define " +
            "a parameter with the same name as a previously defined parameter for the " +
            "method. The VLS states that each parameter to a method must be given a " +
            "unique name that is a valid Virgil identifier.");

    public static final ErrorGen RedundantModifier = new ErrorGen("RedundantModifier",
            "redundant modifier %1",
            "Generated when the declaration of a member of a class or component includes " +
            "an access modifier that is redundant.");

    public static final ErrorGen StatementMustBeInLoop = new ErrorGen("StatementMustBeInLoop",
            "statement must be in loop",
            "Generated when the loop control statements \"break\" and \"continue\" do not " +
            "occur within the body of a loop. In Virgil, these statements can only be placed " +
            "within statements; \"break\" is unnecessary for switch statements.");

    public static final ErrorGen SuperClauseMustBeInClass = new ErrorGen("SuperClauseMustBeInClass",
            "call to super constructor must be inside class",
            "Generated when a component constructor includes a call to a super constructor. " +
            "Components do not have super classes; super constructor clauses are only " +
            "allowed inside the declaration of class constructors.");

    public static final ErrorGen ThisMustBeInClass = new ErrorGen("ThisMustBeInClass",
            "\"this\" is only defined within class methods",
            "Generated when the \"this\" keyword is incorrectly used anywhere but inside " +
            "the body of a class method or constructor. The \"this\" keyword corresponds " +
            "to the receiver object of the method, which only exists in the body of a " +
            "class method.");

    public static final ErrorGen TypeMismatch = new ErrorGen("TypeMismatch",
            "type mismatch in $1: expected %2 and found %3",
            "Generated when the typechecker detects a type error in the program corresponding to " +
            "a situation where an expression or statement expects a certain type and the " +
            "program supplies an expression of an incorrect type. The VLS gives " +
            "a detailed description of the typing rules for Virgil.");

    public static final ErrorGen TypeParameterRedefined = new ErrorGen("TypeParameterRedefined",
            "Type parameter %1 is already defined",
            "Generated when the declaration of a class or method attempts to define " +
            "a type parameter with the same name as a previously defined type parameter for the " +
            "same class or method. The VLS states that each type parameter must be given a " +
            "unique name that is a valid Virgil identifier.");

    public static final ErrorGen TypeParamArityMismatch = new ErrorGen("TypeParamArityMismatch",
            "Type %1 requires %2 type parameters, found %3",
            "Generated when the use of a parameterized type does not match the number of declared " +
            "type parameters. The VLS states that each use of a parameterized type must have the " +
            "same number of type parameters as the declaration.");

    public static final ErrorGen TypeRedefined = new ErrorGen("TypeRedefined",
            "type %1 is already defined at %2",
            "Generated when the program attempts to define a new type that has the " +
            "same name as a previously defined type. The VLS states that each " +
            "declared type must be given a unique name that is a valid Virgil identifier.");

    public static final ErrorGen UnexpectedException = new ErrorGen("UnexpectedException",
            "unexpected exception in reduction of constant: %1",
            "Generated when the compiler reduces the expressions of a case statement to a " +
            "value and the evaluation of the expression results in an exception, such as a " +
            "DivideByZeroException.");

    public static final ErrorGen UnreachableCode = new ErrorGen("UnreachableCode",
            "unreachable code",
            "Generated when the body of a method includes statements beyond the end of " +
            "control flow structures or statements. See the VLS specification for" +
            "a more detailed discussion of the flow structures. ");

    public static final ErrorGen UnresolvedAssignOp = new ErrorGen("UnresolvedAssignOp",
            "unresolved assign operator $1 on type %2",
            "Generated when the program attempts to use a complex assignment operator that " +
            "cannot be applied to the type of the destination. ");

    public static final ErrorGen UnresolvedBinOp = new ErrorGen("UnresolvedBinOp",
            "unresolved operator $1 on types %2, %3",
            "Generated when the program attempts to use an infix binary operator that is either " +
            "unknown or cannot be applied to the types of the operands. The VLS describes " +
            "the procedure by which the compiler resolves infix operators.");

    public static final ErrorGen UnresolvedEntrypoint = new ErrorGen("UnresolvedEntrypoint",
            "unresolved entrypoint %1",
            "Generated when the program defines an entrypoint that does not correspond to a " +
            "supported entrypoint of the selected linkage model or hardware device.");

    public static final ErrorGen UnresolvedIdentifier = new ErrorGen("UnresolvedIdentifier",
            "unresolved identifier %1",
            "Generated when the program makes reference to an identifier that cannot be " +
            "resolved according to the scoping rules of Virgil. The VLS states the " +
            "scoping rules for resolving identifiers (types and variables) in more " +
            "detail.");

    public static final ErrorGen UnresolvedMember = new ErrorGen("UnresolvedMember",
            "unresolved member %1 in %2",
            "Generated when the program makes reference to a member of a type that cannot be " +
            "resolved.");

    public static final ErrorGen UnresolvedOperator = new ErrorGen("UnresolvedOperator",
            "unresolved operator $1",
            "Generated when the program attempts to use an unknown operator.");

    public static final ErrorGen UnresolvedType = new ErrorGen("UnresolvedType",
            "unresolved type %1",
            "Generated when the program makes reference to a type that cannot be resolved.");

    public static final ErrorGen UnresolvedUnaryOp = new ErrorGen("UnresolvedUnaryOp",
            "unresolved operator $1 on type %2",
            "Generated when the program attempts to use an unary operator that is either " +
            "unknown or cannot be applied to the type of the operand. The VLS describes " +
            "the procedure by which the compiler resolves unary operators.");

    public static final ErrorGen VariableNotInitialized = new ErrorGen("VariableNotInitialized",
            "variable %1 is not initialized before use",
            "Generated upon the attempted use of a local variable " +
            "that has not been initialized before use. The VLS states that all local variables " +
            "must be assigned a definite value before the first use, either at the point " +
            "of declaration or at some control flow point that precedes the variable use.");

    public static final ErrorGen VariableRedefined = new ErrorGen("VariableRedefined",
            "variable %1 is already defined in this scope",
            "Generated when the program attempts to define " +
            "a variable with the same name as a previously defined variable in the same " +
            "scope. The VLS states that each variable within a scope must be given a " +
            "unique name that is a valid Virgil identifier.");

    public static final ErrorGen VoidReturn = new ErrorGen("VoidReturn",
            "return value expected",
            "Generated when the body of a method that has been declared to return a non-void " +
            "value includes a return statement that does not have a value. The VLS states " +
            "that each method that is declared to return a non-void type must contain at " +
            "least one return statement at the flow end(s) of the method that returns a " +
            "value.");

    public static SourceError MissingProgramDecl(Program p) {
        SourcePoint pt = p.getDefaultSourcePoint();
        return new SourceError("MissingProgramDecl", pt, "required program declaration missing", StringUtil.EMPTY_STRING_ARRAY);
    }

    public static HelpCategory newErrorHelpCategory() {
        HelpCategory ecat = new HelpCategory("errors", "Help for specific source-level compiler errors.");
        ecat.addSection("VIRGIL LANGUAGE ERRORS", "The Virgil Programming Language Specification defines " +
                "the criteria for a well-formed Virgil program. The compiler checks a number of " +
                "properties of the program to ensure compliance with the language specification " +
                "and generates error messages if the program does not meet the criteria. This help " +
                "category lists the errors that may be generated by the Virgil compiler and gives a " +
                "short description of each.");
        ecat.addSubcategorySection("ALL ERRORS", "Below is a listing of all errors generated by the " +
                "Virgil compiler.", allErrors);

        return ecat;
    }
}
