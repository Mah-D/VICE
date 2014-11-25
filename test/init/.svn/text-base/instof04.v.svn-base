// @Harness: v2-init
// @Test: operation of instanceof construct
// @Result: PASS

class instof04_a {
}

class instof04_b extends instof04_a {
}

component instof04 {
    field foo: instof04_a = new instof04_b();
    field bar: bool = foo instanceof instof04_b;
}

/* 
heap {
    record #0:2:instof04 {
        field foo: instof04_a = #1:instof04_b;
        field bar: bool = bool:true;
    }
    record #1:0:instof04_b {
    }
}*/
