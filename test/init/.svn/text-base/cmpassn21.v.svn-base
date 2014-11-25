// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

class cmpassn21_obj {
    field foo: int = 6;
}

component cmpassn21 {
    field foo: cmpassn21_obj;
    field bar: int = obj().foo += 4;
    
    // this method should only be called once
    method obj(): cmpassn21_obj {
	return foo = new cmpassn21_obj();
    }
}

/* 
heap {
    record #0:2:cmpassn21 {
        field foo: cmpassn21_obj = #1:cmpassn21_obj;
        field bar: int = int:10;
    }
    record #1:1:cmpassn21_obj {
	field foo: int = int:10;
    }
}*/
