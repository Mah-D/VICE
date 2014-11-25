// @Harness: v2-seman
// @Test: TypeQuery (instanceof) operation
// @Result: PASS

class instof02a_01 {
    field bar: instof02a_02;
    field foo: bool = bar instanceof instof02a_01;
}

class instof02a_02 extends instof02a_01 {
}
