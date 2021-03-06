// @Harness: v2-seman
// @Result: PASS

class inh_method08_a extends inh_method08_b<int, bool> {
  field h: int = f();
  field j: bool = g();

  field k: int = x();
  field l: bool = y();
}

class inh_method08_b<T, U> extends inh_method08_c<T, U> {
  method x(): T;
  method y(): U;
}

class inh_method08_c<Y, Z> {
  method f(): Y;
  method g(): Z;
}
