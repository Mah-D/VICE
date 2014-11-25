// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

class this02_obj {
    field foo: int = 1;
    field bar: int;
    constructor() {
        bar = this.foo + 3;
    }
}

component this02 {
    field foo: this02_obj = new this02_obj();
}

/* 
heap {
    record #0:1:this02 {
        field foo: this02_obj = #1:this02_obj;
    }
    record #1:2:this02_obj {
        field foo: int = int:1;
        field bar: int = int:4;
    }
} */
