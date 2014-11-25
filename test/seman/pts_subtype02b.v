// @Harness: v2-seman
// @Result: TypeMismatch @ 5:29

class subtype02_a<X> extends subtype02_b<X> {
  field f: subtype02_a<X> = new subtype02_b<X>();
}
class subtype02_b<X> {
}
