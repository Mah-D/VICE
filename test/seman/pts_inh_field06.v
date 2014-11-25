// @Harness: v2-seman
// @Result: PASS

class inh_field06_a<X> extends inh_field06_b<X, int> {
  field h: X = f;
  field j: int = g;
}

class inh_field06_b<Y, Z> {
  field f: Y;
  field g: Z;
}
