// @Harness: v2-init
// @Test: arithmetic operators
// @Result: PASS

component raw_comp01 {
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
	return ~a;
    }
}

/* 
heap {
    record #0:10:raw_comp01 {
        field res_01: raw.32 = raw.32:0x00000001;
        field res_02: raw.32 = raw.32:0x00000000;
        field res_03: raw.32 = raw.32:0xfffffbb3;
        field res_04: raw.32 = raw.32:0xffffffff;
        field res_05: raw.32 = raw.32:0x0000000c;
        field res_06: raw.32 = raw.32:0xfffffffe;
        field res_07: raw.32 = raw.32:0x00000010;
        field res_08: raw.32 = raw.32:0x0000ffff;
        field res_09: raw.32 = raw.32:0xffffff00;
        field res_10: raw.32 = raw.32:0xfff0bdbf;
    }
} */
