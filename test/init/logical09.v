// @Harness: v2-init
// @Test: logical operators
// @Result: PASS

component logical09 {
    field res_01: bool;
    field res_02: bool;
    field res_03: bool;
    field res_04: bool;

    constructor() {
	res_01 = true or false;
	res_02 = true or true;
	res_03 = false or true;
	res_04 = false or false;
    }

}

/* 
heap {
    record #0:4:logical09 {
	field res_01: bool = bool:true;
	field res_02: bool = bool:true;
	field res_03: bool = bool:true;
	field res_04: bool = bool:false;
    }
} */
