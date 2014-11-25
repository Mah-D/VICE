// @Harness: v2-init
// @Result: PASS

class C<X> {
}

component mp_init01 {
  field f: C<int> = makeC();

  method makeC<T>(): C<T> {
    return new C<T>();
  }
}

/*
heap {
  record #0:1:mp_init01 {
    field f: C<int> = #1:C<int>;
  }
  record #1:0:C<int> {
  }
}
*/
