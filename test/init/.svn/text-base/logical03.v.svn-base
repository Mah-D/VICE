// @Harness: v2-init
// @Test: logical operators
// @Result: PASS

component logical03 {
    field res_01: bool = op(true);
    field res_02: bool = op(false);

    method op(a: bool): bool {
	return !a;
    }
}

/* 
heap {
    record #0:2:logical03 {
        field res_01: bool = bool:false;
        field res_02: bool = bool:true;
    }
} */
