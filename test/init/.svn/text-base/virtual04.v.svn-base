// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class virtual04_a {
    field th: virtual04_a;
    method val(a: int): int { th = this; return a+1; }
}

class virtual04_b extends virtual04_a {
    method val(a: int): int { th = this; return a+2; }
}

class virtual04_c extends virtual04_a {
    method val(a: int): int { th = this; return a+3; }
}

component virtual04 {
    field a: virtual04_a = new virtual04_a();
    field b: virtual04_a = new virtual04_b();
    field c: virtual04_a = new virtual04_c();
    field av: int = a.val(10);
    field bv: int = b.val(10);
    field cv: int = c.val(10);
}

/* 
heap {
    record #0:6:virtual04 {
        field a: virtual04_a = #1:virtual04_a;
        field b: virtual04_a = #2:virtual04_b;
        field c: virtual04_a = #3:virtual04_c;
        field av: int = int:11;
        field bv: int = int:12;
        field cv: int = int:13;
    }
    record #1:1:virtual04_a {
	field th: virtual04_a = #1:virtual04_a;
    }
    record #2:1:virtual04_b {
	field th: virtual04_a = #2:virtual04_b;
    }
    record #3:1:virtual04_c {
	field th: virtual04_a = #3:virtual04_c;
    }
} */
