// @Harness: v2-seman
// @Result: PASS

class scope03 {
  method first<X>(x: X): X {
    return x;
  }

  method second<X>(x: X, y: X): X {
    return y;
  }

}
