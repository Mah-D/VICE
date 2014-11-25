// @Harness: v2-seman
// @Test: lvalue correctness (pre-increment operator)
// @Result: NotAnLvalue @ 7:10

class lvalue18 {
    
    method testm(): int {
        2 = testm();
        return 0;
    }
}
