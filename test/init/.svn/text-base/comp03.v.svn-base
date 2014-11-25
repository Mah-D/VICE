// @Harness: v2-init
// @Test: integers comparison operators
// @Result: PASS

component comp03 {
    field res_01: bool = op(1, 2);
    field res_02: bool = op(2, 1);
    field res_03: bool = op(-1, 1);
    field res_04: bool = op(-1, 0);
    field res_05: bool = op(-200, -200);
    field res_06: bool = op(65535, 65535);
    field res_07: bool = op(14, 13);
    field res_08: bool = op(13, 14);
    field res_09: bool = op(-1255, -255);
    field res_10: bool = op(1000000, 48576);

    method op(a: int, b: int): bool {
	return a <= b;
    }
}

/* 
heap {
    record #0:10:comp03 {
        field res_01: bool = bool:true;
        field res_02: bool = bool:false;
        field res_03: bool = bool:true;
        field res_04: bool = bool:true;
        field res_05: bool = bool:true;
        field res_06: bool = bool:true;
        field res_07: bool = bool:false;
        field res_08: bool = bool:true;
        field res_09: bool = bool:true;
        field res_10: bool = bool:false;
    }
} */
