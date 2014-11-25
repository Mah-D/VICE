// @Harness: v2-init
// @Test: arithmetic operators
// @Result: PASS

component arith13 {
    field res_01: int = op(1, -2);
    field res_02: int = op(-8, 3);
    field res_03: int = op(-1, -1);
    field res_04: int = op(0, -1);
    field res_05: int = op(-200, -13);
    field res_06: int = op(65535, -5);
    field res_07: int = op(13455, -17);
    field res_08: int = op(64, 8);
    field res_09: int = op(255, 19);
    field res_10: int = op(-48576, 1000);

    method op(a: int, b: int): int {
	return (a / b) * b + (a % b);
    }
}

/* 
heap {
    record #0:10:arith13 {
        field res_01: int = int:1;
        field res_02: int = int:-8;
        field res_03: int = int:-1;
        field res_04: int = int:0;
        field res_05: int = int:-200;
        field res_06: int = int:65535;
        field res_07: int = int:13455;
        field res_08: int = int:64;
        field res_09: int = int:255;
        field res_10: int = int:-48576;
    }
} */
