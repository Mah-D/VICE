// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class virtual05_a {
    method val(): int { return 1; }
}

class virtual05_b extends virtual05_a {
}

class virtual05_c extends virtual05_b {
    method val(): int { return 3; }
}

component virtual05 {
    field a: virtual05_a = new virtual05_a();
    field b: virtual05_a = new virtual05_b();
    field c: virtual05_a = new virtual05_c();
    field av: int = a.val();
    field bv: int = b.val();
    field cv: int = c.val();
}

/* 
heap {
    record #0:6:virtual05 {
        field a: virtual05_a = #1:virtual05_a;
        field b: virtual05_a = #2:virtual05_b;
        field c: virtual05_a = #3:virtual05_c;
        field av: int = int:1;
        field bv: int = int:1;
        field cv: int = int:3;
    }
    record #1:0:virtual05_a {
    }
    record #2:0:virtual05_b {
    }
    record #3:0:virtual05_c {
    }
} */
