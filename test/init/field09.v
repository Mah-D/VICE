// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

class field09_obj {
    field baz: int = 2;
    method bar();
}

component field09 {
    field foo: function() = new field09_obj().bar;
}

/* 
heap {
    record #0:1:field09 {
        field foo: function() = [#1:field09_obj,field09_obj:bar()];
    }
    record #1:1:field09_obj {
        field baz: int = int:2;
    }
} */
