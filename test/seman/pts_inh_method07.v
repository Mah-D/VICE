// @Harness: v2-seman
// @Result: PASS

class inh_method07_a extends inh_method07_b {
  field h: int = f();
  field j: bool = g();
}

class inh_method07_b extends inh_method07_c<int, bool> {
}

class inh_method07_c<Y, Z> {
  method f(): Y;
  method g(): Z;
}
