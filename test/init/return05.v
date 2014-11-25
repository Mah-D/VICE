// @Harness: v2-init
// @Test: return statement
// @Result: PASS

component return05 {
    
    field x: int;

    constructor() {
	x = m();
	if ( true ) return;
    }

    method m(): int {
	return 11;
    }
}

/* 
heap {
    record #0:1:return05 {
        field x: int = int:11;
    }
} */
