// @Harness: v2-seman
// @Result: CannotOverrideParamType @ 5:21

class ovr_method08_a<X> extends ovr_method08_b<X, int> {
  method f(x: X, y: X): X;
}

class ovr_method08_b<X, Y> {
  method f(x: X, y: Y): X;
}
