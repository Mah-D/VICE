// @Harness: v2-seman
// @@Test: "break statement must be inside of loop"
// @Result: StatementMustBeInLoop @ 6:17

class break2 {

    method testm() {
        if ( true ) break;
    }
}
