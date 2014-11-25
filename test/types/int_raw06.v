// @Harness: v2-seman
// @Test: typechecking; int type
// @Result: PASS

class int_raw06 {
//    field a: boolean = (0x00 == 0);
//    field b: boolean = (0x0000 == 0);
//    field c: boolean = (0x000000 == 0);
    field d: boolean = (0x000000000 == 0);
}
