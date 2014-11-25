// @Harness: v2-init
// @Test: arithmetic operators
// @Result: PASS

component raw_flip05 {
    field res_01: 16 = flip2(0x0ffe);
    field res_02: 16 = flip2(0x0002);
    field res_03: 16 = flip2(0x0001);
    field res_04: 16 = flip2(0x8000);
    field res_05: 16 = flip2(0xf007);
    field res_06: 16 = flip2(0x8877);
    field res_07: 16 = flip2(0xaa01);

    method flip(a: 16): 16 {
	return (a :: 8) # ((a >> 8) :: 8);
    }

    method flip2(a: 16): 16 {
	return flip(flip(a));
    }
}

/* 
heap {
    record #0:6:raw_flip05 {
        field res_01: raw.16 = raw.16:0x0ffe;
        field res_02: raw.16 = raw.16:0x0002;
        field res_03: raw.16 = raw.16:0x0001;
        field res_04: raw.16 = raw.16:0x8000;
        field res_05: raw.16 = raw.16:0xf007;
        field res_06: raw.16 = raw.16:0x8877;
        field res_07: raw.16 = raw.16:0xaa01;
    }
} */
