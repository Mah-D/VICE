// @Harness: v2-seman
// @Result: PASS

class inh_field02b_a<X> extends inh_field02b_b<int> {
  field g: int = this.f;
}

class inh_field02b_b<X> {
  field f: X;
}
