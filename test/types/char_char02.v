// @Harness: v2-seman
// @Test: typechecking; char type
// @Result: PASS

class char_char02 {
    field a: char;
    field b: char = a :: char;
    field c: char = 0 :: char :: char;
}
