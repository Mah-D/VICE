// @Harness: v2-seman
// @Result: PASS

class inh_method03_a<Y> extends inh_method03_b<Y> {
  field g: Y = f();
}

class inh_method03_b<X> {
  method f(): X;
}
