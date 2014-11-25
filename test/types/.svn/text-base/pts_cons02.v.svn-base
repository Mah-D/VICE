// @Harness: v2-seman
// @Result: PASS

class W<T> {
    field val: T;
    constructor(t: T) {
        val = t;
    }
}

class V<T> extends W<T> {
   constructor(t: T) : super(t) { }
}

component pts_cons01 {
   field w1: V<char> = new V<char>('c');
   field w2: V<int> = new V<int>(0);
}
