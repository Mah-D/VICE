// @Harness: v2-seman
// @Test: typechecking > raw types > exact bit sizes > binary constants
// @Result: TypeMismatch @ 6:18

class raw_bin02 {
    field a: 1 = 0b01;
}
