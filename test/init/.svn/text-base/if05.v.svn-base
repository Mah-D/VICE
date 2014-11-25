// @Harness: v2-init
// @Test: if statements and ternary expressions
// @Result: PASS

class chooser05 {
    field A: int;
    field B: int;
    field R: int;

    method choose(c: bool, a: int, b: int) {
	if ( c ) R = A = a;
	else R = B = b;
    }
}

component if05 {
    
    field res_01: chooser05 = new chooser05();
    field res_02: chooser05 = new chooser05();

    constructor() {
	res_01.choose(true, -160, 42);
        res_02.choose(false, -100, 24);
    }

}

/* 
heap {
    record #0:2:if05 {
        field res_01: chooser05 = #1:chooser05;
        field res_02: chooser05 = #2:chooser05;
    }
    record #1:3:chooser05 {
	field A: int = int:-160;
	field B: int = int:0;
	field R: int = int:-160;
    }
    record #2:3:chooser05 {
	field A: int = int:0;
	field B: int = int:24;
	field R: int = int:24;
    }
} */
