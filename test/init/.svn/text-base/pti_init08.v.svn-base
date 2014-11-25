// @Harness: v2-init
// @Result: PASS

class C<X> {
  field g: X[];
}

component init08 {
  field f: C<int> = new C<int>();
  constructor() {
    f.g = new int[1];
  }
}

/*
heap {
  record #0:1:init08 {
    field f: C<int> = #1:C<int>;
  }
  record #1:1:C<int> {
    field g: int[] = #2:int[1];
  }
  record #2:1:int[1] {
    field 0: int = int:0;
  }
}
*/
