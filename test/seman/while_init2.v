// @Harness: v2-seman
// @Test: variable initialization (order of evaluation)
// @Result: PASS

class init34 {
    
    method testm(): bool {
        local b: bool;
        while ( b = true ) return b;
        return false;
    }
}
