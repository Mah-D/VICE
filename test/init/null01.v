// @Harness: v2-init
// @Test: null exceptions
// @Result: NullCheckException @ 7:25

component null01 {
    field foo: int[];
    field bar: int = foo[0];
}
