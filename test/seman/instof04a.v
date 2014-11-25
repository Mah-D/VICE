// @Harness: v2-seman
// @Test: TypeQuery (instanceof) operation
// @Result: ExpectedObjectType @ 7:30

class instof04a_01 {
    field bar: instof04a_01;
    field foo: bool = bar instanceof instof04a_02;
}

component instof04a_02 {
}
