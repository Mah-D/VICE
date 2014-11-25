// @Harness: v2-init
// @Result: PASS

class C<X, Y> {
  field g: X;
  field h: Y;
}

component init06 {
  field f: C<int, char> = new C<int,char>();
  constructor() {
    f.g = -11;
    f.h = 'c';
  }
}

/*
heap {
  record #0:1:init06 {
    field f: C<int,char> = #1:C<int,char>;
  }
  record #1:1:C<int,char> {
    field g: int = int:-11;
    field h: char = char:99;
  }
}
*/
