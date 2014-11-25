// @Harness: v2-seman
// @Result: PASS

class inh_method09_a extends inh_method09_b<int, bool> {
  field h: int = g();
  field j: bool = f();

  field k: int = x();
  field l: bool = y();
}

class inh_method09_b<T, U> extends inh_method09_c<U, T> { // note permutation
  method x(): T;
  method y(): U;
}

class inh_method09_c<Y, Z> {
  method f(): Y;
  method g(): Z;
}
