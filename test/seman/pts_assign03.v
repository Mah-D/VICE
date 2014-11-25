// @Harness: v2-seman
// @Result: PASS

class assign03<X> {
   field f: assign03<X> = null;
   field g: assign03<X> = f;
}
