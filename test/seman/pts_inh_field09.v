// @Harness: v2-seman
// @Result: PASS

class inh_field09_a extends inh_field09_b<int, bool> {
  field h: int = g;
  field j: bool = f;

  field k: int = x;
  field l: bool = y;
}

class inh_field09_b<T, U> extends inh_field09_c<U, T> { // note permutation
  field x: T;
  field y: U;
}

class inh_field09_c<Y, Z> {
  field f: Y;
  field g: Z;
}
