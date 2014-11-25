// @Harness: v2-init
// @Test: return statement
// @Result: PASS

component return01 {
    
    field x: int;

    constructor() {
	m();
    }

    method m() {
	x = 11;
	return;
    }
}

/* 
heap {
    record #0:1:return01 {
        field x: int = int:11;
    }
} */
