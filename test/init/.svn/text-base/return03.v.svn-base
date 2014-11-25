// @Harness: v2-init
// @Test: return statement
// @Result: PASS

component return03 {
    
    field x: int;

    constructor() {
	x = m();
    }

    method m(): int {
	return 11;
    }
}

/* 
heap {
    record #0:1:return03 {
        field x: int = int:11;
    }
} */
