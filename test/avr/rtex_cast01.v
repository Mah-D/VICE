// @Harness: tir-to-avr
// @Test: type check exceptions in AVR code
// @Result: TypeCheckException

program rtex_cast01 {
    entrypoint main = rtex_cast01.main;
}

class rtex_a {
}

class rtex_b extends rtex_a {
}

component rtex_cast01 {
    field foo: rtex_a = new rtex_a();

    method main(): int {
	local x = foo :: rtex_b;
	return 0;
    }
}
