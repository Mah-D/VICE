// @Harness: v2-seman
// @Test: typechecking > raw types > exact bit sizes > binary constants
// @Result: TypeMismatch @ 6:18

class raw_bin05 {
    field a: 4 = 0b11101;
}
