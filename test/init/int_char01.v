// @Harness: v2-init
// @Test: promotions of char->int
// @Result: PASS

component int_char01 {
    field a: int = '0';
    field b: int = '\n';
    field c: int = '\367';
}

/* 
heap {
    record #0:3:int_char01 {
        field a: int = int:48;
        field b: int = int:10;
        field c: int = int:247;
    }
} */
