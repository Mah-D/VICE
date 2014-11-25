// @Harness: v2-seman
// @Result: CannotOverrideReturnType @ 5:15

class ovr_method05_a<X> extends ovr_method05_b<X> {
  method f(): int;
}

class ovr_method05_b<X> {
  method f(): X;
}
