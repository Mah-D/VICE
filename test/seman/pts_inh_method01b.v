// @Harness: v2-seman
// @Result: PASS

class inh_method01b_a<X> extends inh_method01b_b<X> {
  field g: X = this.f();
}

class inh_method01b_b<X> {
  method f(): X;
}
