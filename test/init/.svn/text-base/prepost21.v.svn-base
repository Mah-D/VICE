// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

class prepost21_obj {
    field foo: int = 6;
}

component prepost21 {
    field foo: prepost21_obj;
    field bar: int = obj().foo++;
    
    // this method should only be called once
    method obj(): prepost21_obj {
	return foo = new prepost21_obj();
    }
}

/* 
heap {
    record #0:2:prepost21 {
        field foo: prepost21_obj = #1:prepost21_obj;
        field bar: int = int:6;
    }
    record #1:1:prepost21_obj {
	field foo: int = int:7;
    }
}*/
