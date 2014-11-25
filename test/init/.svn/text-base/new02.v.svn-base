// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class new02_a {
    method getThis(): new02_a { return this; }
}

component new02 {
    field a: new02_a = (new new02_a()).getThis();
}

/* 
heap {
    record #0:6:new02 {
        field a: new02_a = #1:new02_a;
    }
    record #1:0:new02_a {
    }
} */
