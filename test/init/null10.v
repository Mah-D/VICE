// @Harness: v2-init
// @Test: null exceptions
// @Result: NullCheckException @ 7:25

class null10_obj {
    method baz();
}

component null10 {
    field foo: null10_obj = null;
    field bar: function() = foo.baz;
}
