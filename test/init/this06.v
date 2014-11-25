// @Harness: v2-init
// @Test: uses of "this"
// @Result: PASS

class this06b_obj {
    field foo: bool = equals(this);
    method equals(o: this06b_obj): bool { return o == this; }
}

component this06b {
    field foo: this06b_obj = new this06b_obj();
}

/* 
heap {
    record #0:1:this06b {
        field foo: this06b_obj = #1:this06b_obj;
    }
    record #1:1:this06b_obj {
        field foo: bool = bool:true;
    }
} */
