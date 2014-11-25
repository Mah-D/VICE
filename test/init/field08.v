// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component field08 {
    field foo: function() = bar;
    method bar();
}

/* 
heap {
    record #0:1:field08 {
        field foo: function() = [#0:field08,field08:bar()];
    }
} */
