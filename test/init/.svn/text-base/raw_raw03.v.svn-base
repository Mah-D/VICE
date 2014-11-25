// @Harness: v2-init
// @Test: conversion between raw types of various sizes
// @Result: PASS

component raw_raw03 {
    field a: 16 = 0xffff;
    field b: 32 = 0xffffffff;
    field c: 40 = a :: 40; 
    field d: 40 = b :: 40;
    field e: 48 = a :: 48;
    field f: 48 = b :: 48;
    field g: 64 = a :: 64;
    field h: 64 = b :: 64;
}

/* 
heap {
    record #0:14:raw_raw03 {
        field a: 16  = raw.16:0xffff;
        field b: 32  = raw.32:0xffffffff;
        field c: 40  = raw.40:0x000000ffff;
        field d: 40  = raw.40:0x00ffffffff;
        field e: 48  = raw.48:0x00000000ffff;
        field f: 48  = raw.48:0x0000ffffffff;
        field g: 64  = raw.64:0x000000000000ffff;
        field h: 64  = raw.64:0x00000000ffffffff;
    }
} */
