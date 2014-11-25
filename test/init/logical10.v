// @Harness: v2-init
// @Test: logical operators
// @Result: PASS

component logical10 {
    field res_01: int;
    field res_02: int;
    field res_03: int;
    field res_04: int;

    constructor() {
	res_01 = true and false ? 11 : 21;
	res_02 = true and true ? 11 : 21;
	res_03 = false and true ? 11 : 21;
	res_04 = false and false ? 11 : 21;
    }

}

/* 
heap {
    record #0:4:logical10 {
	field res_01: int = int:21;
	field res_02: int = int:11;
	field res_03: int = int:21;
	field res_04: int = int:21;
    }
} */
