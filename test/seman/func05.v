// @Harness: v2-seman
// @Test: function types, method extraction
// @Result: PASS

class func05 {
    method apply(f: function(int):int, a: int): int {
        return apply(extract, a);
    }
    method extract(a: int): int {
        return 0;
    }
}
