// @Harness: v2-seman
// @Result: TypeParamArityMismatch @ 9:14

class num_params05_a<X> {
}

component num_params05_b {
  method m(o: num_params05_a<int, bool>) {
  }
}
