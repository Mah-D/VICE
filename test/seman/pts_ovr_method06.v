// @Harness: v2-seman
// @Result: CannotOverrideReturnType @ 5:15

class ovr_method06_a extends ovr_method06_b<int> {
  method f(): bool;
}

class ovr_method06_b<X> {
  method f(): X;
}
