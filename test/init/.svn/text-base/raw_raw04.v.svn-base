// @Harness: v2-init
// @Test: conversion between raw types of various sizes
// @Result: PASS

component raw_raw04 {
    field a: 64 = 0x0123456789abcdef;
    field b: 48 = a :: 48;
    field c: 40 = a :: 40; 
    field d: 32 = a :: 32;
    field e: 16 = a :: 16;
    field f: 8  = a :: 8;
    field g: 4  = a :: 4;
    field h: 3  = a :: 3;
    field i: 1  = a :: 1;
}

/* 
heap {
    record #0:14:raw_raw04 {
        field a: 64  = raw.64:0x0123456789abcdef;
        field b: 48  = raw.48:0x456789abcdef;
        field c: 40  = raw.40:0x6789abcdef;
        field d: 32  = raw.32:0x89abcdef;
        field e: 16  = raw.16:0xcdef;
        field f: 8   = raw.8:0xef;
        field g: 4   = raw.4:0xf;
        field h: 3   = raw.3:0x7;
        field i: 1   = raw.1:0x1;
    }
} */
