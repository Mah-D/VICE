// @Harness: v2-init
// @Test: arithmetic operators
// @Result: PASS

component arith10 {
    field res_01: int = op(-2);
    field res_02: int = op(-1);
    field res_03: int = op(1100);
    field res_04: int = op(0);
    field res_05: int = op(-13);
    field res_06: int = op(1);
    field res_07: int = op(-17);
    field res_08: int = op(-65536);
    field res_09: int = op(255);
    field res_10: int = op(1000000);

    method op(a: int): int {
	return -a;
    }
}

/* 
heap {
    record #0:10:arith10 {
        field res_01: int = int:2;
        field res_02: int = int:1;
        field res_03: int = int:-1100;
        field res_04: int = int:0;
        field res_05: int = int:13;
        field res_06: int = int:-1;
        field res_07: int = int:17;
        field res_08: int = int:65536;
        field res_09: int = int:-255;
        field res_10: int = int:-1000000;
    }
} */
