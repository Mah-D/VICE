// @Harness: v2-init
// @Test: conversion between raw types of various sizes
// @Result: PASS

component raw_raw02 {
    field a: 32 = 0xffffffff;
    field b: 24 = a :: 24;
    field c: 16 = a :: 16;
    field d: 12 = a :: 12;
    field e: 8  = a :: 8;
    field f: 4  = a :: 4;
    field g: 3  = a :: 3;
    field h: 1  = a :: 1;
}

/* 
heap {
    record #0:14:raw_raw02 {
        field a: 32 = raw.32:0xffffffff;
        field b: 24 = raw.24:0xffffff;
        field c: 16 = raw.16:0xffff;
        field d: 12 = raw.12:0xfff;
        field e: 8  = raw.8:0xff;
        field f: 4  = raw.4:0xf;
        field g: 3  = raw.3:0x7;
        field h: 1  = raw.1:0x1;
    }
} */
