// @Harness: v2-init
// @Test: array index exceptions
// @Result: BoundsCheckException @ 7:25

component index04 {
    field foo: int[] = new int[16];
    field bar: int = foo[16];
}
