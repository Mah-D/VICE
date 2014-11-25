// @Harness: v2-seman
// @Test: Virgil constructors
// @Result: ArgumentCountMismatchInNew @ 13:34

class constructor04 {

    field f: int;

    constructor() {
        f = 1;
    }

    method testm(): constructor04 {
        return new constructor04(0);
    }
}
