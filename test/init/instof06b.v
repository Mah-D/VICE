// @Harness: v2-init
// @Test: TypeQuery operator
// @Result: PASS

class instof06b_a {
}

class instof06b_b extends instof06b_a {
}

class instof06b_c extends instof06b_b {
}

component instof06b {
    field foo: instof06b_a = new instof06b_c();
    field bar: bool = foo <: instof06b_b;
}

/* 
heap {
    record #0:2:instof06b {
        field foo: instof06b_a = #1:instof06b_c;
        field bar: bool = bool:true;
    }
    record #1:0:instof06b_c {
    }
}*/
