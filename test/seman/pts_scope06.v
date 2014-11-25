// @Harness: v2-seman
// @Result: TypeParameterRedefined @ 4:18

class scope06<X, X> {
  method first(x: X): X {
    return x;
  }
}
