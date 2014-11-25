// @Harness: v2-init
// @Test: conversion between raw types of various sizes
// @Result: PASS

component raw_raw01 {
    field a: 4  = 0xf;
    field b: 8  = a :: 8;
    field c: 16 = a :: 16;
    field d: 32 = a :: 32;
}

/* 
heap {
    record #0:14:raw_raw01 {
        field a: 4  = raw.4:0xf;
        field b: 8  = raw.8:0xf;
        field c: 16 = raw.16:0xf;
        field d: 32 = raw.32:0xf;
    }
} */
