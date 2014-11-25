// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

class cmpassn22_obj {
    field foo: int = 6;
}

component cmpassn22 {
    field foo: cmpassn22_obj;
    field bar: int = obj().foo += 4;
    
    // this method should only be called once
    method obj(): cmpassn22_obj {
	return foo = new cmpassn22_obj();
    }
}

/* 
heap {
    record #0:2:cmpassn22 {
        field foo: cmpassn22_obj = #1:cmpassn22_obj;
        field bar: int = int:10;
    }
    record #1:1:cmpassn22_obj {
	field foo: int = int:10;
    }
}*/
