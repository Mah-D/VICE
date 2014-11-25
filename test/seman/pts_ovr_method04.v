// @Harness: v2-seman
// @Result: PASS

class ovr_method04_a<X> extends ovr_method04_b<X, int> {
  method f(x: X, y: int): X;
}

class ovr_method04_b<X, Y> {
  method f(x: X, y: Y): X;
}
