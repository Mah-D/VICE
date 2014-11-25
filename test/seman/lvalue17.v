// @Harness: v2-seman
// @Test: lvalue correctness (pre increment operator)
// @Result: NotAnLvalue @ 7:7

class lvalue17 {
    
    method testm(): int {
        testm() = 2;
        return 0;
    }
}
