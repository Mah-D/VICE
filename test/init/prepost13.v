// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

class prepost13_obj {
    field foo: int = 1;
    field bar: int = inc();
    method inc(): int {
	local i = foo;
        foo = i++;
        return i;
    }
}

component prepost13 {
    field foo: prepost13_obj = new prepost13_obj();
}

/* 
heap {
    record #0:1:prepost13 {
        field foo: prepost13_obj = #1:prepost13_obj;
    }
    record #1:2:prepost13_obj {
        field foo: int = int:1;
        field bar: int = int:2;
    }
}*/
