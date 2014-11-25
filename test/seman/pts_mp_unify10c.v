// @Harness: v2-seman
// @Result: TypeMismatch @ 11:58

class A<Z> { }
class B<X> extends A<X> { }
class C<Y> extends A<Y> { }

component mp_unify09 {
    method test1(b: B<int>, c: C<char>): A<int> { return id(null, null); }
    method test2(b: B<int>, c: C<char>): A<int> { return id(b, null); }
//    method test3(b: B<int>, c: C<char>): A<int> { return id(null, c); }
    method test4(b: B<int>, c: C<char>): A<int> { return id(b, c); }

    method id<X>(x: X, y: X): X {
	return x;
    }
}
