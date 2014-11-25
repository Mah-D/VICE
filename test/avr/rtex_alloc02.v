// @Harness: tir-to-avr
// @Test: allocation exceptions in AVR code
// @Result: AllocationException

program rtex_alloc02 {
    entrypoint main = rtex_alloc02.main;
}

component rtex_alloc02 {
    method main(): int {
	local x = { 1 };
	return x[0];
    }
}
