// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

class cmpassn13_obj {
    field foo: int = 1;
    field bar: int = inc();
    method inc(): int {
	local i = foo;
        foo = i += 3;
        return i;
    }
}

component cmpassn13 {
    field foo: cmpassn13_obj = new cmpassn13_obj();
}

/* 
heap {
    record #0:1:cmpassn13 {
        field foo: cmpassn13_obj = #1:cmpassn13_obj;
    }
    record #1:2:cmpassn13_obj {
        field foo: int = int:4;
        field bar: int = int:4;
    }
}*/
