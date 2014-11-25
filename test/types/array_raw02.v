// @Harness: v2-seman
// @Test: typechecking; char type
// @Result: PASS

class array_raw02 {
    field a: 8[] = { 'c', 0b00, 0x4e };
    field b: 16[] = { 0xefef, 0b1100101, '7', '\n' };
    field c: 32[] = { 07755642, 0b000000000, -1, 2009 };

    field av: 8  = a[0];
    field bv: 16 = b[2];
    field cv: 32 = b[11];
}
