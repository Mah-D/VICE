// @Harness: v2-init
// @Test: if statements and ternary expressions
// @Result: PASS

component cmpassn26 {
    
    field b: int;

    constructor() {
        local i = 0;
        b += i++;
        b += i++;
    }
}

/* 
heap {
    record #0:1:cmpassn26 {
        field b: int = int:1;
    }
} */
