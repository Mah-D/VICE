// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

class prepost22_obj {
    field foo: int = 6;
}

component prepost22 {
    field foo: prepost22_obj;
    field bar: int = ++obj().foo;
    
    // this method should only be called once
    method obj(): prepost22_obj {
	return foo = new prepost22_obj();
    }
}

/* 
heap {
    record #0:2:prepost22 {
        field foo: prepost22_obj = #1:prepost22_obj;
        field bar: int = int:7;
    }
    record #1:1:prepost22_obj {
	field foo: int = int:7;
    }
}*/
