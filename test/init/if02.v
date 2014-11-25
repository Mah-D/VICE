// @Harness: v2-init
// @Test: if statements and ternary expressions
// @Result: PASS

component if02 {
    
    field res_01: int = choose(true,  -1, 1);
    field res_02: int = choose(false, -1, 1);
    field res_03: int = choose(true,  241, 100);
    field res_04: int = choose(false, 241, 100);
    field res_05: int = choose(true,  -100, 6);
    field res_06: int = choose(false, -100, 6);

    method choose(c: bool, a: int, b: int): int {
	if ( c ) return a;
        return b;
    }
}

/* 
heap {
    record #0:6:if02 {
        field res_01: int = int:-1;
        field res_02: int = int:1;
        field res_03: int = int:241;
        field res_04: int = int:100;
        field res_05: int = int:-100;
        field res_06: int = int:6;
    }
} */
