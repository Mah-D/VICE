// @Harness: tir-to-avr
// @Test: allocation exceptions in AVR code
// @Result: AllocationException

program rtex_alloc03 {
    entrypoint main = rtex_alloc03.main;
}

class rtex_alloc03_obj {
    field bar: int = 0;
}

component rtex_alloc03 {
    method main(): int {
	return (new rtex_alloc03_obj()).bar;
    }
}
