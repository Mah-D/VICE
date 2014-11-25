// @Harness: v2-init
// @Test: parsing precedence of arithmetic operators
// @Result: PASS

component prec01 {
    field r01: int = 1 + 2 * 3;
    field r02: int = 1 + 3 * 2;
    field r03: int = 6 * 1 / 2;
    field r04: int = 2 / 1 * 8;
    field r05: int = 4 / 1 / 2;
    field r06: int = 3 - 2 * 3;
    field r07: int = 6 - 4 / 2;
    field r08: int = 6 - 4 * 2 + 7;
    field r09: int = 6 * 4 - 7 / 2;
}

/* 
heap {
    record #0:10:prec01 {
        field r01: int = int:7;
        field r02: int = int:7;
	field r03: int = int:3;
	field r04: int = int:16;
	field r05: int = int:2;
	field r06: int = int:-3;
	field r07: int = int:4;
	field r08: int = int:5;
	field r09: int = int:21;
    }
} */
