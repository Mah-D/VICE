// @Harness: tir-to-avr
// @Test: divide exceptions in AVR code
// @Result: DivideByZeroException

program rtex_div01 {
    entrypoint main = rtex_div01.main;
}

component rtex_div01 {
    field foo: int = 0;

    method main(): int {
	return 1 / foo;
    }
}
