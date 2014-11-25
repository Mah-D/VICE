// @Harness: v2-init
// @Test: TypeQuery operator
// @Result: PASS

class instof04b_a {
}

class instof04b_b extends instof04b_a {
}

component instof04b {
    field foo: instof04b_a = new instof04b_b();
    field bar: bool = foo <: instof04b_b;
}

/* 
heap {
    record #0:2:instof04b {
        field foo: instof04b_a = #1:instof04b_b;
        field bar: bool = bool:true;
    }
    record #1:0:instof04b_b {
    }
}*/
