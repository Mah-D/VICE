// @Harness: v2-init
// @Test: compile-time constants for primitive types
// @Result: PASS

component const_int02 {
    field a: int = -0;
    field b: int = -1;
    field c: int = -9;
    field d: int = -15;
    field e: int = -255;
    field f: int = -256;
    field g: int = -32768;
    field h: int = -65535;
    field i: int = -65536;
    field j: int = -1048576;
    field k: int = 2147483647;
    field l: int = -2147483648;
}

/* 
heap {
    record #0:12:const_int02 {
        field a: int = int:0;
        field b: int = int:-1;
        field c: int = int:-9;
        field d: int = int:-15;
        field e: int = int:-255;
        field f: int = int:-256;
        field g: int = int:-32768;
        field h: int = int:-65535;
        field i: int = int:-65536;
        field j: int = int:-1048576;
        field k: int = int:0x7fffffff;
        field l: int = int:0x80000000;
    }
} */
