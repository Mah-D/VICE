// @Harness: v2-init
// @Test: array index exceptions
// @Result: BoundsCheckException @ 12:17

component index07 {
    field foo: int[] = new int[42];
    field bar: int = baz();

    method baz(): int {
        local cntr: int;
        for ( cntr = 41; cntr >= -1; cntr-- )
	    foo[cntr] = cntr;
        return -4;
    }
}
