// @Harness: v2-seman
// @Result: PASS

class assign04<X, Y> {
   field f: assign04<X, Y> = null;
   field g: assign04<X, Y> = f;
}
