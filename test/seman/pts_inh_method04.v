// @Harness: v2-seman
// @Result: PASS

class inh_method04_a extends inh_method04_b<int> {
  field g: int = f();
}

class inh_method04_b<X> {
  method f(): X;
}
