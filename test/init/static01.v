// @Harness: v2-init
// @Test: static method invocations
// @Result: PASS

component static01_a {
    method val(): int { return 1; }
}

component static01_b {
    method val(): int { return 2; }
}

component static01_c {
    method val(): int { return 3; }
}

component static01 {
    field av: int = static01_a.val();
    field bv: int = static01_b.val();
    field cv: int = static01_c.val();
}

/* 
heap {
    record #0:0:static01_a {
    }
    record #1:0:static01_b {
    }
    record #2:0:static01_c {
    }
    record #3:3:static01 {
        field av: int = int:1;
        field bv: int = int:2;
        field cv: int = int:3;
    }
} */
