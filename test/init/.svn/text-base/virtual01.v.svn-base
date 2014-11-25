// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class virtual01_a {
    method val(): int { return 1; }
}

class virtual01_b extends virtual01_a {
    method val(): int { return 2; }
}

class virtual01_c extends virtual01_a {
    method val(): int { return 3; }
}

component virtual01 {
    field a: virtual01_a = new virtual01_a();
    field b: virtual01_a = new virtual01_b();
    field c: virtual01_a = new virtual01_c();
    field av: int = a.val();
    field bv: int = b.val();
    field cv: int = c.val();
}

/* 
heap {
    record #0:6:virtual01 {
        field a: virtual01_a = #1:virtual01_a;
        field b: virtual01_a = #2:virtual01_b;
        field c: virtual01_a = #3:virtual01_c;
        field av: int = int:1;
        field bv: int = int:2;
        field cv: int = int:3;
    }
    record #1:0:virtual01_a {
    }
    record #2:0:virtual01_b {
    }
    record #3:0:virtual01_c {
    }
} */
