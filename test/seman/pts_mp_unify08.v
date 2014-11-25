// @Harness: v2-seman
// @Result: PASS

component mp_unify08 {
    method test1(): int[] { return id(null, null); }
    method test2(): char[] { return id(null, null); }
    method test3(): 32[] { return id(null, null); }
    method test4(): 8[] { return id(null, null); }

    method id<X>(x: X, y: X): X {
	return x;
    }
}
