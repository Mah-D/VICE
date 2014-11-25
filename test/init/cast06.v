// @Harness: v2-init
// @Test: TypeCast operator
// @Result: PASS

class cast06_a {
}

class cast06_b extends cast06_a {
}

class cast06_c extends cast06_b {
}

component cast06 {
    field foo: cast06_a = new cast06_c();
    field bar: cast06_b = foo :: cast06_b;
}

/* 
heap {
    record #0:2:cast06 {
        field foo: cast06_a = #1:cast06_c;
        field bar: cast06_b = #1:cast06_c;
    }
    record #1:0:cast06_c {
    }
}*/
