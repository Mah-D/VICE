// @Harness: v2-init
// @Test: uses of "this"
// @Result: PASS

class this01b_obj {
    field foo: int = 1;
    field bar: int = this.foo + 3;
}

component this01b {
    field foo: this01b_obj = new this01b_obj();
}

/* 
heap {
    record #0:1:this01b {
        field foo: this01b_obj = #1:this01b_obj;
    }
    record #1:2:this01b_obj {
        field foo: int = int:1;
        field bar: int = int:4;
    }
} */
