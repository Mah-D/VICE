// @Harness: v2-seman
// @Test: typechecking; subtyping relations between objects
// @Result: PASS

class type25_a {
}
class type25_b extends type25_a {
    
    method testm() {
        local x: type25_a = new type25_b();
    }
}
