// // @Harness: v2-parse
// // @Test:
// // @Result: PASS

class ASTPrinter extends DepthFirstVisitor {

    field o: PrintStream;
    field begLine: bool;

    method visit01(n: ClassDecl) {
        println("class "+d.token+" {");
        indent();
        unindent();
        println("}");
    }

    method visit02(n: ComponentDecl) {
        println("component "+d.token+" {");
        indent();
        unindent();
        println("}");
    }

    method visit03(n: FieldDecl) {
        d.type.accept(this);
        print(" "+d.token);
        if ( d.init != null ) {
            print(" = ");
            d.init.accept(this);
        }
        println(";");
    }

    method visit04(n: MethodDecl) {
        d.returnType.accept(this);
        print(" "+d.token+"(");
        d.params.accept(this);
        if ( d.block != null ) {
            print(") ");
            d.block.accept(this);
        }
        else println(");");
    }

    method visit05(n: Block) {
        println("{");
        indent();
        b.stmts.accept(this);
        unindent();
        println("}");
    }

    method visit06(n: WhileStmt) {
        print("while ( ");
        s.cond.accept(this);
        print(" ) ");
        s.body.accept(this);
        nextln();
    }

    method visit07(n: SwitchStmt) {
        print("switch ( ");
        s.value.accept(this);
        println(" ) {");
        indent();
        s.cases.accept(this);
        unindent();
        nextln();
        println("}");
    }

    method visit08(n: SwitchCase) {
        if ( c.list != null ) {
            print("case ( ");
            c.list.accept(this);
            print(" ) ");
        } else {
            print("default ");
        }
        c.stmt.accept(this);
        nextln();
    }

    method visit09(n: DoWhileStmt) {
        print("do ");
        s.body.accept(this);
        print("while ( ");
        s.cond.accept(this);
        println(" );");
    }

    method visit10(n: ListExpr) {
        local prev = false;
        local iter = l.iterator();
        while ( iter.hasNext() ) {
            local e = iter.next();
            if ( prev ) print(", ");
            e.accept(this);
            prev = true;
        }
    }

    method visit11(n: ListParamDecl) {
        local prev = false;
        local iter = l.iterator();
        while ( iter.hasNext() ) {
            local p = iter.next();
            if ( prev ) print(", ");
            p.accept(this);
        }

    }
    method visit12(n: ForStmt) {
        print("for ( ");
        s.init.accept(this);
        print("; ");
        if ( s.cond != null ) s.cond.accept(this);
        print("; ");
        s.update.accept(this);
        if ( !s.update.isEmpty() ) print(" "); // slight space adjustment
        print(") ");
        s.body.accept(this);
        nextln();
    }

    method visit13(n: IfStmt) {
        print("if ( ");
        s.cond.accept(this);
        print(" ) ");
        s.trueStmt.accept(this);
        if ( !(s.falseStmt instanceof EmptyStmt) ) {
            print("else ");
            s.falseStmt.accept(this);
        }
        nextln();
    }

    method visit14(n: BreakStmt) {
        println("break;");
    }

    method visit15(n: ContinueStmt) {
        println("continue;");
    }

    method visit16(n: ReturnStmt) {
        print("return");
        if ( s.expr != null ) {
            print(" ");
            s.expr.accept(this);
        }
        println(";");
    }

    method visit17(n: EmptyStmt) {
        println(";");
    }

    method visit18(n: ExprStmt) {
        s.expr.accept(this);
        println(";");
    }

    method visit19(n: Assign) {
        embed(a, a.target);
        print(" = ");
        embed(a, a.value);
    }
    // helper for parentheses
    method embed(out: Expr, in: Expr) {
        if ( in.getPrecedence() < out.getPrecedence() ) print("(");
        in.accept(this);
        if ( in.getPrecedence() < out.getPrecedence() ) print(")");
    }

    method visit20(n: TernaryExpr) {
        embed(e, e.cond);
        print(" ? ");
        embed(e, e.trueExpr);
        print(":");
        embed(e, e.falseExpr);
    }

    method visit21(n: BinOp) {
        embed(b, b.left);
        print(" "+b.token+" ");
        embed(b, b.right);
    }

    method visit22(n: UnaryOp) {
        embed(u, u.expr);
    }

    method visit23(n: PlainCall) {
        print(c.token+"(");
        c.params.accept(this);
        print(")");
    }

    method visit24(n: FieldExpr) {
        embed(e, e.expr);
        print("."+e.token);
    }

    method visit25(n: ElementExpr) {
        embed(e, e.array);
        print("[");
        e.index.accept(this);
        print("]");
    }

    method visit26(n: ExprCall) {
        embed(c, c.expr);
        print("."+c.token+"(");
        if ( c.params != null ) c.params.accept(this);
        print(")");
    }

    method visit27(n: NewObjectExpr) {
        print("new ");
        n.type.accept(this);
        print("(");
        if ( n.args != null ) n.args.accept(this);
        print(")");
    }

    method visit28(n: NewArrayExpr) {
        print("new ");
        n.type.accept(this);
        print("[");
        if ( n.list != null ) n.list.accept(this);
        print("]");
    }

    method visit29(n: LocalVarDecl) {
        d.type.accept(this);
        print(" "+d.token);
        if ( d.init != null ) {
            print(" = ");
            d.init.accept(this);
        }
        println(";");
    }

    method visit30(n: VarUse) {
        print(u.token.toString());
    }

    method visit31(n: IntLiteral) {
        print(l.token.toString());
    }

    method visit32(n: BooleanLiteral) {
        print(l.token.toString());
    }

    method visit33(n: ThisLiteral) {
        print(l.token.toString());
    }

    method visit34(n: NullLiteral) {
        print(l.token.toString());
    }

    method visit35(n: CharLiteral) {
        print(l.token.toString());
    }

    method visit36(n: StringLiteral) {
        print(l.token.toString());
    }

    method visit37(n: ParamDecl) {
        p.type.accept(this);
        print(" "+p.token);
    }

    method visit38(n: TypeRef) {
        print(r.type.toString());
    }

    method println(s: String) {
        spaces();
        o.println(s);
        begLine = true;
    }

    method print(s: String) {
        spaces();
        o.print(s);
    }

    method nextln() {
        if ( !begLine ) {
            o.print("\n");
            begLine = true;
        }
    }

    method indent() {
        indent++;
    }

    method spaces() {
        if ( begLine ) {
            local cntr: int;
            for ( cntr = 0; cntr < indent; cntr++ )
                o.print("    ");
            begLine = false;
        }
    }

    method unindent() {
        indent--;
        if ( indent < 0 ) indent = 0;
    }

}
