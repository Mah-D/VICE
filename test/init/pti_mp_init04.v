// @Harness: v2-init
// @Result: PASS

class C<X> {
}

component mp_init04 {
  field f: C<int> = makeC();
  field g: C<char> = makeC();

  method makeC<T>(): C<T> {
    return new C<T>();
  }
}

/*
heap {
  record #0:2:mp_init04 {
    field f: C<int> = #1:C<int>;
    field g: C<char> = #2:C<char>;
  }
  record #1:0:C<int> {
  }
  record #2:0:C<char> {
  }
}
*/
