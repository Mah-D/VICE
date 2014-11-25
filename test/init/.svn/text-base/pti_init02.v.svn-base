// @Harness: v2-init
// @Result: PASS

class C<X> {
  field g: X;
}

component init02 {
  field f: C<int> = new C<int>();
}

/*
heap {
  record #0:1:init02 {
    field f: C<int> = #1:C<int>;
  }
  record #1:0:C<int> {
    field g: int = int:0;
  }
}
*/
