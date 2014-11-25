// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

class field10_obj {
    field baz: int;
    constructor() {
        baz = 2;
    }
    method bar();
}

component field10 {
    field foo: function() = new field10_obj().bar;
}

/* 
heap {
    record #0:1:field10 {
        field foo: function() = [#1:field10_obj,field10_obj:bar()];
    }
    record #1:1:field10_obj {
        field baz: int = int:2;
    }
} */
