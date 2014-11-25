// @Harness: v2-seman
// @Test: typechecking; char type
// @Result: PASS

class char_raw03 {
    field a: boolean = (0xff == 'a');
    field b: boolean = ('a' == 0xff);
}
