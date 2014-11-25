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
    local p = new Pair<int, bool>();
    local b: int = p.a;
    local c: bool = p.b;
  }
}
