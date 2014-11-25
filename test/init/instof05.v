// @Harness: v2-init
// @Test: operation of instanceof construct
// @Result: PASS

class instof05_a {
}

class instof05_b extends instof05_a {
}

class instof05_c extends instof05_a {
}

component instof05 {
    field foo: instof05_a = new instof05_c();
    field bar: bool = foo instanceof instof05_b;
}

/* 
heap {
    record #0:2:instof05 {
        field foo: instof05_a = #1:instof05_c;
        field bar: bool = bool:false;
    }
    record #1:0:instof05_c {
    }
}*/
