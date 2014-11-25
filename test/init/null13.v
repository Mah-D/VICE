// @Harness: v2-init
// @Test: null exceptions
// @Result: NullCheckException @ 7:25

class null13_obj {
    field foo: int[];
    field bar: int = bang();
    method bang(): int {
        return foo[0];
    }
}

component null13 {
    field baz: null13_obj = new null13_obj();
}
