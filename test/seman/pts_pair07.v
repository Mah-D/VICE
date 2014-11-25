// @Harness: v2-seman
// @Result: PASS

class Pair<X, Y> {
  field a: X;
  field b: Y;

  method first(): X {
    return a;
  }

  method second(): Y {
    return b;
  }
}

component Client {
  method test() {
    local p = makePair(0, false);
  }

  method makePair<T, U>(x: T, y: U): Pair<T, U> {
    local v = new Pair<T, U>();
    v.a = x;
    v.b = y;
    return v;
  }
}
