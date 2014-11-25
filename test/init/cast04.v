// @Harness: v2-init
// @Test: TypeCast operator
// @Result: PASS

class cast04_a {
}

class cast04_b extends cast04_a {
}

component cast04 {
    field foo: cast04_a = new cast04_b();
    field bar: cast04_b = foo :: cast04_b;
}

/* 
heap {
    record #0:2:cast04 {
        field foo: cast04_a = #1:cast04_b;
        field bar: cast04_b = #1:cast04_b;
    }
    record #1:0:cast04_b {
    }
}*/
