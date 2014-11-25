// @Harness: v2-seman
// @Test: typechecking; incorrect number of arguments in call
// @Result: ArgumentCountMismatch @ 10:9

class type06 {
    
    method foo(): int {
        return 0;
    }
    
    method testm() {
        foo(1);
    }
}
