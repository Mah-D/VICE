// @Harness: v2-seman
// @Test: typechecking; char type
// @Result: TypeMismatch @ 6:19

class array_raw06 {
    field a: 7[] = { 0xf, 0b00, 0xfe };
}
