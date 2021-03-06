// @Harness: v2-init
// @Test: arithmetic operators
// @Result: PASS

component arith03 {
    field res_01: int = op(1, -2);
    field res_02: int = op(2, -1);
    field res_03: int = op(-1, -1);
    field res_04: int = op(-1, 0);
    field res_05: int = op(-200, -13);
    field res_06: int = op(65535, -1);
    field res_07: int = op(0, -17);
    field res_08: int = op(255, -12);
    field res_09: int = op(255, 255);
    field res_10: int = op(1000, -48576);

    method op(a: int, b: int): int {
	return a * b;
    }
}

/* 
heap {
    record #0:10:arith03 {
        field res_01: int = int:-2;
        field res_02: int = int:-2;
        field res_03: int = int:1;
        field res_04: int = int:0;
        field res_05: int = int:2600;
        field res_06: int = int:-65535;
        field res_07: int = int:0;
        field res_08: int = int:-3060;
        field res_09: int = int:65025;
        field res_10: int = int:-48576000;
    }
} */
