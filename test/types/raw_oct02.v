// @Harness: v2-seman
// @Test: typechecking > raw types > exact bit sizes > hexadecimal constants
// @Result: InvalidLiteral @ 6:19

class raw_oct02 {
    field n: 64 = 01111111111111111111111;
}
