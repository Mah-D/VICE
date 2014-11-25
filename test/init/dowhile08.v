// @Harness: v2-init
// @Test: if statements and ternary expressions
// @Result: PASS

component dowhile08 {
    
    field res_01: int = count(1);
    field res_02: int = count(2);
    field res_03: int = count(3);
    field res_04: int = count(10);
    field res_05: int = count(100);
    field res_06: int = count(200);

    method count(max: int): int {
	local i = 1, cumul = 0, loop = 1;
        do {
            for ( i = 1; ; cumul += i, i++ ) {
	        if ( i > max ) break;
	        else continue;
	    }
	} while (++loop < 2);
        return cumul;
    }
}

/* 
heap {
    record #0:6:dowhile08 {
        field res_01: int = int:1;
        field res_02: int = int:3;
        field res_03: int = int:6;
        field res_04: int = int:55;
        field res_05: int = int:5050;
        field res_06: int = int:20100;
    }
} */
