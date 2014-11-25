// @Harness: v2-init
// @Test: arithmetic operators
// @Result: PASS

component raw_flip04 {
    field res_01: 4 = flip(0xe);
    field res_02: 4 = flip(0x2);
    field res_03: 4 = flip(0x1);
    field res_04: 4 = flip(0x8);
    field res_05: 4 = flip(0xf);
    field res_06: 4 = flip(0x7);
    field res_07: 4 = flip(0xa);

    method flip(a: 4): 4 {
	return a[0] # a[1] # a[2] # a[3];
    }
}

/* 
heap {
    record #0:6:raw_flip04 {
        field res_01: raw.4 = raw.4:0x7;
        field res_02: raw.4 = raw.4:0x4;
        field res_03: raw.4 = raw.4:0x8;
        field res_04: raw.4 = raw.4:0x1;
        field res_05: raw.4 = raw.4:0xf;
        field res_06: raw.4 = raw.4:0xe;
        field res_07: raw.4 = raw.4:0x5;
    }
} */
