// @Harness: v2-seman
// @Result: TypeParamArityMismatch @ 9:14

class num_params04_a {
}

component num_params04_b {
  method m(o: num_params04_a<int, bool>) {
  }
}
