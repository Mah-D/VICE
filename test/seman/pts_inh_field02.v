// @Harness: v2-seman
// @Result: PASS

class inh_field02_a<X> extends inh_field02_b<int> {
  field g: int = f;
}

class inh_field02_b<X> {
  field f: X;
}
