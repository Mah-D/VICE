// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

class prepost14_obj {
    field foo: int = 1;
    field bar: int = inc();
    method inc(): int {
	local i = foo;
	return ++i;
    }
}

component prepost14 {
    field foo: prepost14_obj = new prepost14_obj();
}

/* 
heap {
    record #0:1:prepost14 {
        field foo: prepost14_obj = #1:prepost14_obj;
    }
    record #1:2:prepost14_obj {
        field foo: int = int:1;
	field bar: int = int:2;
    }
}*/
