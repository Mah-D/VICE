// @Harness: v2-init
// @Test: TypeQuery operator
// @Result: PASS

class instof05b_a {
}

class instof05b_b extends instof05b_a {
}

class instof05b_c extends instof05b_a {
}

component instof05b {
    field foo: instof05b_a = new instof05b_c();
    field bar: bool = foo <: instof05b_b;
}

/* 
heap {
    record #0:2:instof05b {
        field foo: instof05b_a = #1:instof05b_c;
        field bar: bool = bool:false;
    }
    record #1:0:instof05b_c {
    }
}*/
