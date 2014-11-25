// @Harness: v2-init
// @Test: parsing precedence of arithmetic operators
// @Result: PASS

component prec02 {
    field r01: int = (1 + 2) * 3;
    field r02: int = (1 + 3) * 2;
    field r03: int = 6 * (1 / 2);
    field r04: int = 2 / (1 * 8);
    field r05: int = 4 / (4 / 2);
}

/* 
heap {
    record #0:10:prec02 {
        field r01: int = int:9;
        field r02: int = int:8;
	field r03: int = int:0;
	field r04: int = int:0;
	field r05: int = int:2;
    }
} */
