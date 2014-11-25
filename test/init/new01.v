// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class new01_a {
}

component new01 {
    field a: new01_a = new new01_a();
}

/* 
heap {
    record #0:6:new01 {
        field a: new01_a = #1:new01_a;
    }
    record #1:0:new01_a {
    }
} */
