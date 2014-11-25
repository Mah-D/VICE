// @Harness: v2-seman
// @Result: PASS

class assign02<X> {
   field f: assign02<X>;
   field g: assign02<X> = f;
}
