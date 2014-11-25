// @Harness: v2-seman
// @Test: Virgil constructors
// @Result: TypeMismatch @ 13:34

class constructor08 {

    field f: int;

    constructor(a: int) {
        f = a;
    }

    method testm(): constructor08 {
        return new constructor08(false);
    }
}
