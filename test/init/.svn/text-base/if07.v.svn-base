// @Harness: v2-init
// @Test: if statements and ternary expressions
// @Result: PASS

component if07 {
    
    field res_01: int = main(0);
    field res_02: int = main(1);
    field res_03: int = main(2);
    field res_04: int = main(3);
    field res_05: int = main(4);
    field res_06: int = main(5);

    method main(arg: int): int {
	if ( arg > 1 ) if ( arg == 2 ) return 3; else return 4;
	return 5;
    }
}

/*
heap {
    record #0:6:if07 {
        field res_01: int = int:5;
        field res_02: int = int:5;
        field res_03: int = int:3;
        field res_04: int = int:4;
        field res_05: int = int:4;
        field res_06: int = int:4;
    }
}
*/
