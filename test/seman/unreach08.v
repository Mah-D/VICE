// @Harness: v2-seman
// @Test: unreachable code
// @Result: UnreachableCode @ 12:8

class unreach08 {
    
    method testm(): int {
        local foo: int;
        local bar: int[] = { 0, 1 };
        foo = 2;
        bar[0] = 3;
        bar[1] = foo + bar[1] - 9 * foo >> 2;
        return foo;
        return bar;
    }
}
