// @Harness: v2-seman
// @Result: TypeMismatch @ 6:13

class assign14<X> {
   field f: assign14<int> = null;
   field g: assign14<X> = f;
}
