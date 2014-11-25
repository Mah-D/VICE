// @Harness: v2-init
// @Result: PASS

class C<X, Y> {
  field g: X;
  field h: Y;
}

component init05 {
  field f: C<int, int> = new C<int, int>();
  constructor() {
    f.g = -11;
    f.h = 'c';
  }
}

/*
heap {
  record #0:1:init05 {
    field f: C<int,int> = #1:C<int,int>;
  }
  record #1:1:C<int,int> {
    field g: int = int:-11;
    field h: int = int:99;
  }
}
*/
