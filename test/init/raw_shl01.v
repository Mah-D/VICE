// @Harness: v2-init
// @Test: arithmetic operators
// @Result: PASS

component raw_shl01 {
    field res_01: 32 = op(0x0f, 1);
    field res_02: 32 = op(2, 2);
    field res_03: 32 = op(1, 31);
    field res_04: 32 = op(0, 8);
    field res_05: 32 = op(0xf0, 16);
    field res_06: 32 = op(65535, 2);
    field res_07: 32 = op(0x10000, 16);
    field res_08: 32 = op(64, 32);
    field res_09: 32 = op(255, 5);
    field res_10: 32 = op(0xaa, 0);

    method op(a: 32, b: int): 32 {
	return a << b;
    }
}

/* 
heap {
    record #0:10:raw_shl01 {
        field res_01: raw.32 = raw.32:0x1e;
        field res_02: raw.32 = raw.32:0x8;
        field res_03: raw.32 = raw.32:0x80000000;
        field res_04: raw.32 = raw.32:0x0;
        field res_05: raw.32 = raw.32:0xf00000;
        field res_06: raw.32 = raw.32:0x3fffc;
        field res_07: raw.32 = raw.32:0x0;
        field res_08: raw.32 = raw.32:0x0;
        field res_09: raw.32 = raw.32:0x1fe0;
        field res_10: raw.32 = raw.32:0xaa;
    }
} */
