// @Harness: v2-seman
// @Test: TypeQuery (instanceof) operation
// @Result: PASS

class instof02b_01 {
    field bar: instof02b_02;
    field foo: bool = bar <: instof02b_01;
}

class instof02b_02 extends instof02b_01 {
}
