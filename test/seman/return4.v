// @Harness: v2-seman
// @Test: return correctness
// @Result: MissingReturn @ 9:3

class return4 {
    
    method testm(): int {
        if ( false ) {
            return 1;
        }
    }
}
