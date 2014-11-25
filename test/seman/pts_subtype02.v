// @Harness: v2-seman
// @Result: PASS

class subtype02_a<X> extends subtype02_b<X> {
  field f: subtype02_b<X> = new subtype02_a<X>();
}
class subtype02_b<X> {
}
