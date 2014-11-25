// @Harness: v2-init
// @Test: compile-time constants for primitive types
// @Result: PASS

component const_char01 {
    field a: char = 'a';
    field b: char = 'b';
    field c: char = '\n';
    field d: char = '\t';
    field e: char = '\b';
    field f: char = '\r';
    field g: char = '\'';
    field h: char = '"';
    field i: char = '\377';
    field j: char = '\\';
    field k: char = ' ';
    field l: char = ' ';
    field m: char = ' ';
    field n: char = ' ';
}

/* 
heap {
    record #0:14:const_char01 {
        field a: char = char:97;
        field b: char = char:98;
        field c: char = char:10;
        field d: char = char:9;
        field e: char = char:8;
        field f: char = char:13;
        field g: char = char:39;
        field h: char = char:34;
        field i: char = char:255;
        field j: char = char:92;
        field k: char = char:32;
        field l: char = char:32;
        field m: char = char:32;
        field n: char = char:32;
    }
} */
