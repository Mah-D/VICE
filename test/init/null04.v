// @Harness: v2-init
// @Test: null exceptions
// @Result: NullCheckException @ 11:24

class null04_obj {
    field baz: int;
}

component null04 {
    field foo: null04_obj = null;
    field bar: int = foo.baz;
}
