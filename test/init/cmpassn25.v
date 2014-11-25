// @Harness: v2-init
// @Test: if statements and ternary expressions
// @Result: PASS

component cmpassn25 {
    
    field a: int;
    field b: int;

    constructor() {
        b += a++;
        b += a++;
    }
}

/* 
heap {
    record #0:6:cmpassn25 {
        field a: int = int:2;
        field b: int = int:1;
    }
} */
