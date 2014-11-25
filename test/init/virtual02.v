// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class virtual02_a {
    method val(): int { return 1; }
}

class virtual02_b extends virtual02_a {
    method val(): int { return 2; }
}

class virtual02_c extends virtual02_b {
    method val(): int { return 3; }
}

component virtual02 {
    field a: virtual02_a = new virtual02_a();
    field b: virtual02_a = new virtual02_b();
    field c: virtual02_a = new virtual02_c();
    field av: int = a.val();
    field bv: int = b.val();
    field cv: int = c.val();
}

/* 
heap {
    record #0:6:virtual02 {
        field a: virtual02_a = #1:virtual02_a;
        field b: virtual02_a = #2:virtual02_b;
        field c: virtual02_a = #3:virtual02_c;
        field av: int = int:1;
        field bv: int = int:2;
        field cv: int = int:3;
    }
    record #1:0:virtual02_a {
    }
    record #2:0:virtual02_b {
    }
    record #3:0:virtual02_c {
    }
} */
