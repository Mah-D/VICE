// @Harness: v2-init
// @Test: static method invocations
// @Result: PASS

component static05 {
    field m: function(): int = val1;
    field av: int = m();
    field bv: int = val2();
    field cv: int = val3();

    method val1(): int { return apply(val2, 2); }
    method val2(): int { return 2; }
    method val3(): int { return apply(val1, 4); }    

    method apply(f: function(): int, a: int): int { return f() + a; }
}

/* 
heap {
    record #0:4:static05 {
        field m: function():int = [#0:static05,static05:val1()];
        field av: int = int:4;
        field bv: int = int:2;
        field cv: int = int:8;
    }
} */
