// @Harness: v2-init
// @Test: static method invocations
// @Result: PASS

component static03 {
    field av: int = val1();
    field bv: int = val2();
    field cv: int = val3();

    method val1(): int { return 1; }
    method val2(): int { return val1()+1; }
    method val3(): int { return val2()+1; }
}

/* 
heap {
    record #0:3:static03 {
        field av: int = int:1;
        field bv: int = int:2;
        field cv: int = int:3;
    }
} */
