// @Harness: v2-seman
// @Test: typechecking; field access
// @Result: TypeMismatch @ 10:6

class type13_a {
    field foo: bool;
}
class type13_b {
    
    method testm(x: type13_a) {
        x.foo = 2;
    }
}
