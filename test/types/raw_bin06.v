// @Harness: v2-seman
// @Test: typechecking > raw types > exact bit sizes > binary constants
// @Result: TypeMismatch @ 6:18

class raw_bin06 {
    field a: 5 = 0b011111;
}
