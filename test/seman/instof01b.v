// @Harness: v2-seman
// @Test: TypeQuery (instanceof) operation
// @Result: PASS

class instof01b {
    field bar: instof01b;
    field foo: bool = bar <: instof01b;
}
