// @Harness: v2-init
// @Test: compile-time constants for primitive types
// @Result: PASS

component char_int01 {
    field a: int = 'a';
    field b: int = 'b';
    field c: int = '\n';
    field d: int = '\t';
    field e: int = '\b';
    field f: int = '\r';
    field g: int = '\'';
    field h: int = '"';
    field i: int = '\377';
    field j: int = '\\';
    field k: int = ' ';
}

/* 
heap {
    record #0:11:char_int01 {
        field a: int = int:97;
        field b: int = int:98;
        field c: int = int:10;
        field d: int = int:9;
        field e: int = int:8;
        field f: int = int:13;
        field g: int = int:39;
        field h: int = int:34;
        field i: int = int:255;
        field j: int = int:92;
        field k: int = int:32;
    }
} */
