// @Harness: v2-seman
// @Result: PASS

class A { }
class B extends A { }
class C extends A { }

component mp_unify09 {
    method test1(b: B, c: C): A { return id(null, null); }
    method test2(b: B, c: C): A { return id(b, null); }
    method test3(b: B, c: C): A { return id(null, c); }
    method test4(b: B, c: C): A { return id(b, c); }

    method id<X>(x: X, y: X): X {
	return x;
    }
}
