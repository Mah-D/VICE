// @Harness: tir-to-avr
// @Test: null exceptions in AVR code
// @Result: NullCheckException

program rtex_null01 {
    entrypoint main = rtex_null01.main;
}

component rtex_null01 {
    field foo: int[];

    method main(): int {
	return foo[0];
    }
}
