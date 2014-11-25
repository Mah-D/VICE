// @Harness: v2-seman
// @Test: typechecking; char type
// @Result: PASS

class array_raw05 {
    field a: 9[] = { 0xf, 0b00 };
    field b: 17[] = { 0xefef, 0b1100101 };
    field c: 33[] = { 07755642, 0b000000000 };

    field av: 9  = a[0];
    field bv: 17 = b[2];
    field cv: 33 = b[11];
}
