// @Harness: v2-seman
// @Test: using the char[] type for strings
// @Result: PASS

class string02 {
    field testf: char[];
    method foo(str: char[]) {
        testf = str;
    }
}
