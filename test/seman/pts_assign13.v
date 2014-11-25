// @Harness: v2-seman
// @Result: TypeMismatch @ 6:25

class assign13<X> {
   field f: assign13<int>;
   field g: assign13<X> = f;
}
