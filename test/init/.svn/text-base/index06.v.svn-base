// @Harness: v2-init
// @Test: array index exceptions
// @Result: BoundsCheckException @ 12:17

component index06 {
    field foo: int[] = new int[42];
    field bar: int = baz();

    method baz(): int {
        local cntr: int;
        for ( cntr = 0; cntr <= 42; cntr++ )
	    foo[cntr] = cntr;
        return -4;
    }
}
