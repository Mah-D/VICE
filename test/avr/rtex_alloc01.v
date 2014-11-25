// @Harness: tir-to-avr
// @Test: allocation exceptions in AVR code
// @Result: AllocationException

program rtex_alloc01 {
    entrypoint main = rtex_alloc01.main;
}

component rtex_alloc01 {
    method main(): int {
	return (new int[0])[0];
    }
}
