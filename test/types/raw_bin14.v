// @Harness: v2-seman
// @Test: typechecking > raw types > exact bit sizes > binary constants
// @Result: InvalidLiteral @ 6:19

class raw_bin14 {
    field w: 64 = 0b10101001010010101010100101001010100101000000001010111110101010100;
}
