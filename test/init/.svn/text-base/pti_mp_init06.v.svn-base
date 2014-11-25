// @Harness: v2-init
// @Result: PASS

class C<X> {
  field h: X;
}

component mp_init06 {
  field f: C<int> = makeC(-11);
  field g: C<char> = makeC('c');

  method makeC<T>(x: T): C<T> {
    local o = new C<T>();
    o.h = x;
    return o;
  }
}

/*
heap {
  record #0:2:mp_init06 {
    field f: C<int> = #1:C<int>;
    field g: C<char> = #2:C<char>;
  }
  record #1:0:C<int> {
    field h: int = int:-11;
  }
  record #2:0:C<char> {
    field h: char = char:99;
  }
}
*/
