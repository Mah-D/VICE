// @Harness: v2-seman
// @Test: constructors and inheritance
// @Result: TypeMismatch @ 10:40

class constructor17_a {
    constructor(a: char) {
    }
}

class constructor17_b extends constructor17_a {
    constructor(a: int, b: int): super(a) {
    }
}
