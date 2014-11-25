// @Harness: v2-seman
// @Test: typechecking; char type
// @Result: UnresolvedAssignOp @ 7:18

class char_cmpassn01 {
    method check(a: char, b: char): char {
	return a += b;
    }
}
