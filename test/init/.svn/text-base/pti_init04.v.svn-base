// @Harness: v2-init
// @Result: PASS

class C<X> {
  field g: X;
}

component init04 {
  field f: C<int> = new C<int>();
  field g: C<char> = new C<char>();
  constructor() {
    f.g = -11;
    g.g = 'c';
  }
}

/*
heap {
  record #0:2:init04 {
    field f: C<int> = #1:C<int>;
    field g: C<char> = #2:C<char>;
  }
  record #1:1:C<int> {
    field g: int = int:-11;
  }
  record #2:1:C<char> {
    field g: char = char:99;
  }
}
*/
