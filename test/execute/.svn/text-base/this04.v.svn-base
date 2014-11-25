// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=21, 3=31, 4=42

class this04_a {
    field th: this04_a = this;
    method val(a: int): int { return th == this ? a : 55; }
}

class this04_b extends this04_a {
    method val(a: int): int { return th == this ? a + 10: 55; }
}

class this04_c extends this04_a {
    method val(a: int): int { return th == this ? a + 20: 55; }
}

component this04 {
    field a: this04_a = new this04_a();
    field b: this04_b = new this04_b();
    field c: this04_c = new this04_c();

    method main(arg: int): int {
	if ( arg == 1 ) return a.val(11);
	if ( arg == 2 ) return b.val(11);
	if ( arg == 3 ) return c.val(11);
	return 42;
    }
}
