// @Harness: v2-seman
// @Test: typechecking; char type
// @Result: PASS

class char_char01 {
    field a: char;
    field b: char = a :: char;
    field c: char = 'a' :: char;
}
