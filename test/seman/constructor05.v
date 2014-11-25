// @Harness: v2-seman
// @Test: Virgil constructors
// @Result: PASS

class constructor05 {

    field f: int;

    constructor(a: int) {
        f = a;
    }

    method testm(): constructor05 {
        return new constructor05(0);
    }
}
