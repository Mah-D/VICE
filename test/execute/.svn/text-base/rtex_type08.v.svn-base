// @Harness: v2-exec
// @Test: type check exceptions (i.e. dynamic casts)
// @Result: 0=42, 1=11, 2=12, 3=13, 4=42

class rtex_type08_a {

    method cast_self(): int {
	local x = this :: rtex_type08_a;
	return 11;
    }
}

class rtex_type08_b extends rtex_type08_a {

    method cast_self(): int {
	local x = this :: rtex_type08_b;
	return 12;
    }
}

class rtex_type08_c extends rtex_type08_a {

    method cast_self(): int {
	local x = this :: rtex_type08_c;
	return 13;
    }
}

component rtex_type08 {

    field a: rtex_type08_a = new rtex_type08_a();
    field b: rtex_type08_a = new rtex_type08_b();
    field c: rtex_type08_a = new rtex_type08_c();

    method main(arg: int): int {

	if ( arg == 1 ) return a.cast_self();
	if ( arg == 2 ) return b.cast_self();
	if ( arg == 3 ) return c.cast_self();

	return 42;
    }
}
