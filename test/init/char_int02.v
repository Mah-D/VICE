// @Harness: v2-init
// @Test: compile-time constants for primitive types
// @Result: PASS

component char_int02 {
    field a: bool = 'a' == 97;
    field b: bool = 'b' == 98;
    field c: bool = '\n' == 10;
    field d: bool = '\t' == 9;
    field e: bool = '\b' == 8;
    field f: bool = '\r' == 13;
    field g: bool = '\'' == 39;
    field h: bool = '"' == 34;
    field i: bool = '\377' == 255;
    field j: bool = '\\' == 92;
    field k: bool = ' ' == 32;
}

/* 
heap {
    record #0:11:char_int02 {
        field a: bool = bool:true;
        field b: bool = bool:true;
        field c: bool = bool:true;
        field d: bool = bool:true;
        field e: bool = bool:true;
        field f: bool = bool:true;
        field g: bool = bool:true;
        field h: bool = bool:true;
        field i: bool = bool:true;
        field j: bool = bool:true;
        field k: bool = bool:true;
    }
} */
