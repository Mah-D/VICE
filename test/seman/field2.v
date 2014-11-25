// @Harness: v2-seman
// @Test: cannot declare duplicate fields
// @Result: MemberRedefined @ 6:12

component field2 {
    field testf: int;
    field testf: char;
}
