// @Harness: v2-seman
// @Result: TypeParamArityMismatch @ 9:14

class num_params03_a {
}

component num_params03_b {
  method m(o: num_params03_a<int>) {
  }
}
