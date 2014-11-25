// @Harness: v2-init
// @Test: arithmetic operators
// @Result: PASS

component raw_comp03 {
    field res_01: 32 = op(-2);
    field res_02: 32 = op(-1);
    field res_03: 32 = op(1100);
    field res_04: 32 = op(0);
    field res_05: 32 = op(-13);
    field res_06: 32 = op(1);
    field res_07: 32 = op(-17);
    field res_08: 32 = op(-65536);
    field res_09: 32 = op(255);
    field res_10: 32 = op(1000000);

    method op(a: 32): 32 {
	return (~(a :: 28)) >> 4;
    }
}

/* 
heap {
    record #0:10:raw_comp03 {
        field res_01: raw.32 = raw.32:0x000000;
        field res_02: raw.32 = raw.32:0x000000;
        field res_03: raw.32 = raw.32:0xffffbb;
        field res_04: raw.32 = raw.32:0xffffff;
        field res_05: raw.32 = raw.32:0x000000;
        field res_06: raw.32 = raw.32:0xffffff;
        field res_07: raw.32 = raw.32:0x000001;
        field res_08: raw.32 = raw.32:0x000fff;
        field res_09: raw.32 = raw.32:0xfffff0;
        field res_10: raw.32 = raw.32:0xff0bdb;
    }
} */
