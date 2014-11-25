// @Harness: tir-to-avr
// @Test: null exceptions in AVR code
// @Result: BoundsCheckException

program rtex_bounds01 {
    entrypoint main = rtex_bounds01.main;
}

component rtex_bounds01 {
    field foo: int[] = new int[0];

    method main(): int {
	return foo[0];
    }
}
