// @Harness: v2-seman
// @Test: method resolution
// @Result: UnresolvedIdentifier @ 11:9

class method_res02_a {
    
    method foo() {
    }
}
class method_res02_b extends method_res02_a {
    
    method testm() {
        unres();
    }
}
