// @Harness: v2-init
// @Test: compile-time constants for primitive types
// @Result: PASS

component char_raw02 {
    field a: bool = 'a'    == 0x61;
    field b: bool = 'b'    == 0x62;
    field c: bool = '\n'   == 0x0a;
    field d: bool = '\t'   == 0x09;
    field e: bool = '\b'   == 0x08;
    field f: bool = '\r'   == 0x0d;
    field g: bool = '\''   == 0x27;
    field h: bool = '"'    == 0x22;
    field i: bool = '\377' == 0xff;
    field j: bool = '\\'   == 0x5c;
    field k: bool = ' '    == 0x20;
}

/* 
heap {
    record #0:14:char_raw02 {
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
