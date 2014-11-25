// @Harness: v2-seman
// @Test: cannot declare duplicate fields
// @Result: ExpectedVarType @ 6:17

class field8 {
    field testf: field8c;
}

component field8c {
}
