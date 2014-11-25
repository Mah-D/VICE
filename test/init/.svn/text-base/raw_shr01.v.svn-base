// @Harness: v2-init
// @Test: arithmetic operators
// @Result: PASS

component raw_shr01 {
    field res_01: 32 = op(0x0f, 1);
    field res_02: 32 = op(0x02, 2);
    field res_03: 32 = op(0x80000000, 31);
    field res_04: 32 = op(0xefe, 8);
    field res_05: 32 = op(0xf0, 16);
    field res_06: 32 = op(0xaaaa, 4);
    field res_07: 32 = op(0x10000, 16);
    field res_08: 32 = op(0x700, 8);
    field res_09: 32 = op(-65, 5);
    field res_10: 32 = op(0xaa, 0);
    field res_11: 32 = op(0xaa, 32);

    method op(a: 32, b: int): 32 {
	return a >> b;
    }
}

/* 
heap {
    record #0:10:raw_shr01 {
        field res_01: raw.32 = raw.32:0x00000007;
        field res_02: raw.32 = raw.32:0x00000000;
        field res_03: raw.32 = raw.32:0x00000001;
        field res_04: raw.32 = raw.32:0x0000000e;
        field res_05: raw.32 = raw.32:0x00000000;
        field res_06: raw.32 = raw.32:0x00000aaa;
        field res_07: raw.32 = raw.32:0x00000001;
        field res_08: raw.32 = raw.32:0x00000007;
        field res_09: raw.32 = raw.32:0x07fffffd;
        field res_10: raw.32 = raw.32:0x000000aa;
        field res_11: raw.32 = raw.32:0x00000000;
    }
} */
