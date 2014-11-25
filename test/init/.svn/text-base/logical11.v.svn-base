// @Harness: v2-init
// @Test: logical operators
// @Result: PASS

component logical11 {
    field res_01: int;
    field res_02: int;
    field res_03: int;
    field res_04: int;

    constructor() {
	res_01 = true or false? 11 : 12;
	res_02 = true or true? 11 : 12;
	res_03 = false or true? 11 : 12;
	res_04 = false or false? 11 : 12;
    }

}

/* 
heap {
    record #0:4:logical11 {
	field res_01: int = int:11;
	field res_02: int = int:11;
	field res_03: int = int:11;
	field res_04: int = int:12;
    }
} */
