// @Harness: v2-init
// @Test: return statement
// @Result: PASS

component return04 {
    
    field x: int;

    constructor() {
	x = m();
	return;
    }

    method m(): int {
	return 11;
    }
}

/* 
heap {
    record #0:1:return04 {
        field x: int = int:11;
    }
} */
