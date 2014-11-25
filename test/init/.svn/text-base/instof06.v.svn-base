// @Harness: v2-init
// @Test: operation of instanceof construct
// @Result: PASS

class instof06_a {
}

class instof06_b extends instof06_a {
}

class instof06_c extends instof06_b {
}

component instof06 {
    field foo: instof06_a = new instof06_c();
    field bar: bool = foo instanceof instof06_b;
}

/* 
heap {
    record #0:2:instof06 {
        field foo: instof06_a = #1:instof06_c;
        field bar: bool = bool:true;
    }
    record #1:0:instof06_c {
    }
}*/
