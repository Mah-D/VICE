// @Harness: v2-seman
// @Result: PASS

class subtype01_a<X> extends subtype01_b<X> {
  field f: subtype01_b<int> = new subtype01_a<int>();
}
class subtype01_b<X> {
}
