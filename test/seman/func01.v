// @Harness: v2-seman
// @Test: function types
// @Result: PASS

class func01 {
    method apply(f: function(int):int, a: int): int {
        return f(a);
    }
}
