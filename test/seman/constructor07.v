// @Harness: v2-seman
// @Test: Virgil constructors
// @Result: NonVoidReturn @ 10:9

class constructor07 {

    field f: int;

    constructor(a: int) {
        f = a;
        return this;
    }
}
