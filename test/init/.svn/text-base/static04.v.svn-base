// @Harness: v2-init
// @Test: static method invocations
// @Result: PASS

component static04 {
    field av: int = val1();
    field bv: int = val2();
    field cv: int = val3();

    method val1(): int { return apply(val2, 2); }
    method val2(): int { return 2; }
    method val3(): int { return apply(val1, 4); }    

    method apply(f: function(): int, a: int): int { return f() + a; }
}

/* 
heap {
    record #0:3:static04 {
        field av: int = int:4;
        field bv: int = int:2;
        field cv: int = int:8;
    }
} */
