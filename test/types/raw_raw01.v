// @Harness: v2-seman
// @Test: typechecking; int type
// @Result: PASS

class raw_raw01 {
    field a: boolean = (0x00 == 0x0);
    field b: boolean = (0x0000 == 0x00);
    field c: boolean = (0x000000 == 0x00000);
    field d: boolean = (0x00000000 == 0x0000000);
    field e: boolean = (0x0000000000 == 0x0000000);
    field f: boolean = (0x000000000000 == 0x0000000);
    field g: boolean = (0x0000000000000000 == 0x0000000);
}
