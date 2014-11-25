// @Harness: v2-seman
// @Test: typechecking; char type
// @Result: PASS

class array_raw03 {
    method test() {
        local a = { 0xf, 0b00 };
        local b = { 0xefef, 0b1100101 };
        local c = { 07755642, 0b000000000 };

        local av: 8  = a[0];
        local bv: 16 = b[2];
        local cv: 32 = b[11];
    }
}
