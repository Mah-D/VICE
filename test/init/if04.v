// @Harness: v2-init
// @Test: if statements and ternary expressions
// @Result: PASS

class chooser {
    field A: int;
    field B: int;
    field R: int;

    method choose(c: bool, a: int, b: int) {
	R = c ? (A = a) : (B = b);
    }
}

component if04 {
    
    field res_01: chooser = new chooser();
    field res_02: chooser = new chooser();

    constructor() {
	res_01.choose(true, -160, 42);
        res_02.choose(false, -100, 24);
    }

}

/* 
heap {
    record #0:2:if04 {
        field res_01: chooser = #1:chooser;
        field res_02: chooser = #2:chooser;
    }
    record #1:3:chooser {
	field A: int = int:-160;
	field B: int = int:0;
	field R: int = int:-160;
    }
    record #2:3:chooser {
	field A: int = int:0;
	field B: int = int:24;
	field R: int = int:24;
    }
} */
