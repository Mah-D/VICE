// @Harness: v2-seman
// @Result: PASS

class ovr_method03_a<X> extends ovr_method03_b<X, int> {
  method f(): X;
  method g(): int;
}

class ovr_method03_b<X, Y> {
  method f(): X;
  method g(): Y;
}
