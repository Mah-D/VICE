// @Harness: v2-seman
// @Test: typechecking; char type
// @Result: PASS

class array_raw04 {
    method test() {
        local a = { 'c', 0b00, 0x4e };
        local b = { 0xefef, 0b1100101, '7', '\n' };
        local c = { 07755642, 0b000000000, -1, 2009 };

        local av: 8  = a[0];
        local bv: 16 = b[2];
        local cv: 32 = b[11];
    }
}
