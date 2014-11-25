// @Harness: v2-init
// @Test: arithmetic operators
// @Result: PASS

component raw_flip02 {
    field res_01: 16 = flip(0x0ffe);
    field res_02: 16 = flip(0x0002);
    field res_03: 16 = flip(0x0001);
    field res_04: 16 = flip(0x8000);
    field res_05: 16 = flip(0xf007);
    field res_06: 16 = flip(0x8877);
    field res_07: 16 = flip(0xaa01);

    method flip(a: 16): 16 {
	return (a << 8) | ((a >> 8) :: 8);
    }
}

/* 
heap {
    record #0:6:raw_flip02 {
        field res_01: raw.16 = raw.16:0xfe0f;
        field res_02: raw.16 = raw.16:0x0200;
        field res_03: raw.16 = raw.16:0x0100;
        field res_04: raw.16 = raw.16:0x0080;
        field res_05: raw.16 = raw.16:0x07f0;
        field res_06: raw.16 = raw.16:0x7788;
        field res_07: raw.16 = raw.16:0x01aa;
    }
} */
