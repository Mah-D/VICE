// @Harness: v2-init
// @Test: operation of instanceof construct
// @Result: PASS

class instof03_a {
}

class instof03_b extends instof03_a {
}

component instof03 {
    field foo: instof03_a = new instof03_a();
    field bar: bool = foo instanceof instof03_b;
}

/* 
heap {
    record #0:2:instof03 {
        field foo: instof03_a = #1:instof03_a;
        field bar: bool = bool:false;
    }
    record #1:0:instof03_a {
    }
}*/
