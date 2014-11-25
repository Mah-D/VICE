// @Harness: v2-seman
// @Test: function types
// @Result: PASS

class func03 {
    method apply(f: function(int, int):int, a: int): int {
        return f(a, a);
    }
}
