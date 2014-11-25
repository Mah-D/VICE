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

  method makePair<X, T>(x: X, y: T): Pair<X, T> {
    local v = new Pair<X, T>();
    v.a = x;
    v.b = y;
    return v;
  }
}
