// @Harness: v2-seman
// @Result: TypeMismatch @ 12:36

class W<T> {
    field val: T;
    constructor(t: T) {
        val = t;
    }
}

class V<T, U> extends W<U> {
   constructor(t: T, u: U) : super(t) { }
}

component pts_cons01 {
   field w1: V<char, char> = new V<char, char>('c');
   field w2: V<int, int> = new V<int, int>(0);
}
