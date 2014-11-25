// @Harness: v2-seman
// @Result: PASS

class inh_field08_a extends inh_field08_b<int, bool> {
  field h: int = f;
  field j: bool = g;

  field k: int = x;
  field l: bool = y;
}

class inh_field08_b<T, U> extends inh_field08_c<T, U> {
  field x: T;
  field y: U;
}

class inh_field08_c<Y, Z> {
  field f: Y;
  field g: Z;
}
