// @Harness: v2-seman
// @Result: CannotOverrideArity @ 5:10

class ovr_method07_a<X> extends ovr_method07_b<X, int> {
  method f(x: X, y: int, z: bool): X;
}

class ovr_method07_b<X, Y> {
  method f(x: X, y: Y): X;
}
