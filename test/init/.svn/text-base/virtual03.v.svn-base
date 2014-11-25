// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class virtual03_a {
    method val(a: int): int { return a+1; }
}

class virtual03_b extends virtual03_a {
    method val(a: int): int { return a+2; }
}

class virtual03_c extends virtual03_a {
    method val(a: int): int { return a+3; }
}

component virtual03 {
    field a: virtual03_a = new virtual03_a();
    field b: virtual03_a = new virtual03_b();
    field c: virtual03_a = new virtual03_c();
    field av: int = a.val(10);
    field bv: int = b.val(10);
    field cv: int = c.val(10);
}

/* 
heap {
    record #0:6:virtual03 {
        field a: virtual03_a = #1:virtual03_a;
        field b: virtual03_a = #2:virtual03_b;
        field c: virtual03_a = #3:virtual03_c;
        field av: int = int:11;
        field bv: int = int:12;
        field cv: int = int:13;
    }
    record #1:0:virtual03_a {
    }
    record #2:0:virtual03_b {
    }
    record #3:0:virtual03_c {
    }
} */
