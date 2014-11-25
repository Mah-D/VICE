// @Harness: v2-init
// @Test: null exceptions
// @Result: NullCheckException @ 11:24

class null03_obj {
    field baz: int;
}

component null03 {
    field foo: null03_obj;
    field bar: int = foo.baz;
}
