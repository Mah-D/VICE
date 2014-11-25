// @Harness: v2-init
// @Test: compile-time constants for octal integers
// @Result: PASS

component raw_bin01 {
    field a: 1  =                               0b0;
    field b: 1  =                               0b1;
    field c: 2  =                              0b10;
    field d: 3  =                             0b111;
    field e: 4  =                            0b1000;
    field f: 4  =                            0b1001;
    field g: 4  =                            0b1111;
    field h: 8  =                        0b11111111;
    field i: 15 =                 0b111111111111111;
    field j: 16 =                0b1111111111111111;
    field k: 21 =           0b100000000000000000000;
    field l: 32 = 0b1000000000000000000000000000000;
}

/* 
heap {
    record #0:12:raw_bin01 {
        field a: raw.1  = raw.1:0x0;
        field b: raw.1  = raw.1:0x1;
        field c: raw.2  = raw.2:0x2;
        field d: raw.3  = raw.3:0x7;
        field e: raw.4  = raw.4:0x8;
        field f: raw.4  = raw.4:0x9;
        field g: raw.4  = raw.4:0xf;
        field h: raw.8  = raw.8:0xff;
        field i: raw.15 = raw.15:0x7fff;
        field j: raw.16 = raw.16:0xffff;
        field k: raw.20 = raw.21:0x100000;
        field l: raw.32 = raw.32:0x40000000;
    }
} */
