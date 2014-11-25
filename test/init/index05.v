// @Harness: v2-init
// @Test: array index exceptions
// @Result: BoundsCheckException @ 7:25

component index05 {
    field foo: int[] = new int[65536];
    field bar: int = foo[65536];
}
