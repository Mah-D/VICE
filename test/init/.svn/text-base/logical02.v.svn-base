// @Harness: v2-init
// @Test: logical operators
// @Result: PASS

component logical02 {
    field res_01: bool = op(true, true);
    field res_02: bool = op(false, false);
    field res_03: bool = op(true, false);
    field res_04: bool = op(false, true);

    method op(a: bool, b: bool): bool {
	return a or b;
    }
}

/* 
heap {
    record #0:4:logical02 {
        field res_01: bool = bool:true;
        field res_02: bool = bool:false;
        field res_03: bool = bool:true;
        field res_04: bool = bool:true;
    }
} */
