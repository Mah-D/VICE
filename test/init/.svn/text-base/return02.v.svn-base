// @Harness: v2-init
// @Test: return statement
// @Result: PASS

component return02 {
    
    field x: int;

    constructor() {
	m();
    }

    method m() {
	x = 11;
	if ( true ) return;
    }
}

/* 
heap {
    record #0:1:return02 {
        field x: int = int:11;
    }
} */
