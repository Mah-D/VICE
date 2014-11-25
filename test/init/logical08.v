// @Harness: v2-init
// @Test: logical operators
// @Result: PASS

component logical08 {
    field res_01: bool;
    field res_02: bool;
    field res_03: bool;
    field res_04: bool;

    constructor() {
	res_01 = true and false;
	res_02 = true and true;
	res_03 = false and true;
	res_04 = false and false;
    }

}

/* 
heap {
    record #0:4:logical08 {
	field res_01: bool = bool:false;
	field res_02: bool = bool:true;
	field res_03: bool = bool:false;
	field res_04: bool = bool:false;
    }
} */
