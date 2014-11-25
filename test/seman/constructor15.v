// @Harness: v2-seman
// @Test: constructors and inheritance
// @Result: PASS

class constructor15_a {
    constructor(a: int) {
    }
}

class constructor15_b extends constructor15_a {
    constructor(a: int): super(a) {
    }
}
