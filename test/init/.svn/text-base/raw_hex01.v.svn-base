// @Harness: v2-init
// @Test: compile-time constants for octal integers
// @Result: PASS

component raw_hex01 {
    field a: 32 = 0x0;
    field b: 32 = 0x1;
    field c: 32 = 0x7;
    field d: 32 = 0xa;
    field e: 32 = 0xf;
    field f: 32 = 0x1f;
    field g: 32 = 0x3a;
    field h: 32 = 0xff;
    field i: 32 = 0x7fff;
    field j: 32 = 0x10000;
    field k: 32 = 0x100000;
    field l: 32 = 0x80000000;
    field m: 48 = 0xFFFF80000000;
    field n: 64 = 0xFFFF000080000000;
}

/* 
heap {
    record #0:14:raw_hex01 {
        field a: 32 = raw.4:0x0;
        field b: 32 = raw.4:0x1;
        field c: 32 = raw.4:0x7;
        field d: 32 = raw.4:0xa;
        field e: 32 = raw.4:0xf;
        field f: 32 = raw.8:0x1f;
        field g: 32 = raw.8:0x3a;
        field h: 32 = raw.8:0xff;
        field i: 32 = raw.16:0x7fff;
        field j: 32 = raw.16:0x10000;
        field k: 32 = raw.32:0x100000;
        field l: 32 = raw.32:0x80000000;
        field m: 48 = raw.48:0xFFFF80000000;
        field n: 64 = raw.64:0xFFFF000080000000;
    }
} */
