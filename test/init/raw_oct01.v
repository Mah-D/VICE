// @Harness: v2-init
// @Test: compile-time constants for octal integers
// @Result: PASS

component raw_oct01 {
    field a: 3 = 00;
    field b: 3 = 01;
    field c: 3 = 02;
    field d: 3 = 07;
    field e: 6 = 010;
    field f: 6 = 011;
    field g: 6 = 017;
    field h: 9 = 0377;
    field i: 15 = 077777;
    field j: 18 = 0177777;
    field k: 21 = 04000000;
    field l: 30 = 04000000000;
}

/* 
heap {
    record #0:12:raw_oct01 {
        field a: raw.3  = raw.3:0x0;
        field b: raw.3  = raw.3:0x1;
        field c: raw.3  = raw.3:0x2;
        field d: raw.3  = raw.3:0x7;
        field e: raw.6  = raw.6:0x8;
        field f: raw.6  = raw.6:0x9;
        field g: raw.6  = raw.6:0xf;
        field h: raw.9  = raw.9:0xff;
        field i: raw.15 = raw.15:0x7fff;
        field j: raw.18 = raw.18:0xffff;
        field k: raw.21 = raw.21:0x100000;
        field l: raw.30 = raw.30:0x20000000;
    }
} */
