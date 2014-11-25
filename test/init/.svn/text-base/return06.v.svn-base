// @Harness: v2-init
// @Test: return statement
// @Result: PASS

component return06 {
    
    field x: int;

    constructor() {
	x = m();
	if ( true ) return;
    }

    method m(): int {
	if ( true ) return 11;
	return 12;
    }
}

/* 
heap {
    record #0:1:return06 {
        field x: int = int:11;
    }
} */
