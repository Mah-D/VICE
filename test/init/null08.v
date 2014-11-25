// @Harness: v2-init
// @Test: null exceptions
// @Result: NullCheckException @ 11:24

class null08_obj {
    field baz: int;
}

component null08 {
    field foo: null08_obj = null;
    field bar: int = (foo.baz = 0);
}
