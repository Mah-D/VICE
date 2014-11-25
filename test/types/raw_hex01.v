// @Harness: v2-seman
// @Test: typechecking > raw types > exact bit sizes > hexadecimal constants
// @Result: PASS

class raw_hex01 {
    field a: 4  = 0x0;
    field b: 4  = 0xf;
    field c: 8  = 0xe0;
    field d: 8  = 0xef;
    field e: 12 = 0x0e1;
    field f: 12 = 0xfa5;
    field g: 16 = 0x0444;
    field h: 16 = 0xf331;
    field i: 20 = 0x0137a;
    field j: 20 = 0xf25bc;
    field k: 24 = 0x0ccdef;
    field l: 24 = 0xfc0123;
    field m: 28 = 0x0ff7700;
    field n: 28 = 0xf000000;
    field o: 32 = 0x0a010145;
    field p: 32 = 0xffffffff;
    field q: 36 = 0x0ff823711;
    field r: 36 = 0xf083123bb;
    field s: 48 = 0x0123456789ab;
    field t: 48 = 0xfedcba987654;
    field u: 52 = 0x0123456789abc;
    field v: 52 = 0xf00882938aabb;
    field w: 60 = 0x088739289884999;
    field x: 60 = 0xfaaaaaaaaaaaaaa;
    field y: 64 = 0x01111110000fffff;
    field z: 64 = 0xffff00001111aaaa;
}
