// @Harness: v2-seman
// @Test: cannot declare duplicate fields
// @Result: MemberRedefined @ 6:8

class field1 {
    field testf: int;
    field testf: char;
}
