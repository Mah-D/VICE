// @Harness: v2-init
// @Test: arithmetic operators
// @Result: PASS

component raw_align01 {
    field res_01: bool = aligned(0xfa, 0);
    field res_02: bool = aligned(0xf4, 1);
    field res_03: bool = aligned(0xf1, 2);
    field res_04: bool = aligned(0xf0, 3);
    field res_05: bool = aligned(0xff, 4);
    field res_06: bool = aligned(0xf7, 5);
    field res_07: bool = aligned(0xfa, 6);

    method aligned(a: 32, k: int): bool {
	local mask = (0x00000001 << k) :: int;
	return (a & (mask - 1)) == 0x00000000;
    }
}

/* 
heap {
    record #0:6:raw_align01 {
        field res_01: bool = bool:true;
        field res_02: bool = bool:true;
        field res_03: bool = bool:false;
        field res_04: bool = bool:true;
        field res_05: bool = bool:false;
        field res_06: bool = bool:false;
        field res_07: bool = bool:false;
    }
} */
