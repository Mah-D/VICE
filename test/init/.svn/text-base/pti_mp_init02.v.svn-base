// @Harness: v2-init
// @Result: PASS

component mp_init02 {
  field f: int[] = makeArray();

  method makeArray<T>(): T[] {
    return new T[0];
  }
}

/*
heap {
  record #0:1:mp_init02 {
    field f: int[] = #1:int[0];
  }
  record #1:0:int[0] {
  }
}
*/
