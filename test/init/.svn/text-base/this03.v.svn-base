// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

class this03_obj {
    field bar: int = this.baz();
    method baz(): int { return 5; }
}

component this03 {
    field foo: this03_obj = new this03_obj();
}

/* 
heap {
    record #0:1:this03 {
        field foo: this03_obj = #1:this03_obj;
    }
    record #1:1:this03_obj {
        field bar: int = int:5;
    }
} */
