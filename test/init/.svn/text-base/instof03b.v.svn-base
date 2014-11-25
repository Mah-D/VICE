// @Harness: v2-init
// @Test: TypeQuery operator
// @Result: PASS

class instof03b_a {
}

class instof03b_b extends instof03b_a {
}

component instof03b {
    field foo: instof03b_a = new instof03b_a();
    field bar: bool = foo <: instof03b_b;
}

/* 
heap {
    record #0:2:instof03b {
        field foo: instof03b_a = #1:instof03b_a;
        field bar: bool = bool:false;
    }
    record #1:0:instof03b_a {
    }
}*/
