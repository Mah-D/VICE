// @Harness: v2-init
// @Test: static method invocations
// @Result: PASS

component static02_a {
    field foo: int = 1;
    method val(): int { return foo; }
}

component static02_b {
    field baz: int = 2;
    method val(): int { return baz; }
}

component static02_c {
    field bof: int = 3;
    method val(): int { return bof; }
}

component static02 {
    field av: int = static02_a.val();
    field bv: int = static02_b.val();
    field cv: int = static02_c.val();
}

/* 
heap {
    record #0:1:static02_a {
	field foo: int = int:1;
    }
    record #1:1:static02_b {
	field baz: int = int:2;
    }
    record #2:1:static02_c {
	field bof: int = int:3;
    }
    record #3:3:static02 {
        field av: int = int:1;
        field bv: int = int:2;
        field cv: int = int:3;
    }
} */
