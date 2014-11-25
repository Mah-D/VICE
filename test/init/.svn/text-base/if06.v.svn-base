// @Harness: v2-init
// @Test: if statements and ternary expressions
// @Result: PASS

component if06 {
    
    field res_01: int = choose(0, -1, 1, 6);
    field res_02: int = choose(1, -1, 1, 6);
    field res_03: int = choose(2, -1, 1, 6);
    field res_04: int = choose(0, 241, 100, 13);
    field res_05: int = choose(1, 241, 100, 13);
    field res_06: int = choose(2, 241, 100, 13);

    method choose(cond: int, a: int, b: int, c: int): int {
	if ( cond == 0 ) return a;
        else if ( cond == 1 ) return b;
        else return c;
    }
}

/* 
heap {
    record #0:6:if06 {
        field res_01: int = int:-1;
        field res_02: int = int:1;
        field res_03: int = int:6;
        field res_04: int = int:241;
        field res_05: int = int:100;
        field res_06: int = int:13;
    }
} */
