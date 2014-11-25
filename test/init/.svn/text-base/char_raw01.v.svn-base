// @Harness: v2-init
// @Test: compile-time constants for primitive types
// @Result: PASS

component char_raw01 {
    field a: 8 = 'a';
    field b: 8 = 'b';
    field c: 8 = '\n';
    field d: 8 = '\t';
    field e: 8 = '\b';
    field f: 8 = '\r';
    field g: 8 = '\'';
    field h: 8 = '"';
    field i: 8 = '\377';
    field j: 8 = '\\';
    field k: 8 = ' ';
}

/* 
heap {
    record #0:14:char_raw01 {
        field a: raw.8 = raw.8:0x61;
        field b: raw.8 = raw.8:0x62;
        field c: raw.8 = raw.8:0x0a;
        field d: raw.8 = raw.8:0x09;
        field e: raw.8 = raw.8:0x08;
        field f: raw.8 = raw.8:0x0d;
        field g: raw.8 = raw.8:0x27;
        field h: raw.8 = raw.8:0x22;
        field i: raw.8 = raw.8:0xff;
        field j: raw.8 = raw.8:0x5c;
        field k: raw.8 = raw.8:0x20;
    }
} */
