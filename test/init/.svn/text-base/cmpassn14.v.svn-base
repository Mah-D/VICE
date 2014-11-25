// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

class cmpassn14_obj {
    field foo: int = 1;
    field bar: int = inc();
    method inc(): int {
	local i = foo;
	return i -= 8;
    }
}

component cmpassn14 {
    field foo: cmpassn14_obj = new cmpassn14_obj();
}

/* 
heap {
    record #0:1:cmpassn14 {
        field foo: cmpassn14_obj = #1:cmpassn14_obj;
    }
    record #1:2:cmpassn14_obj {
        field foo: int = int:1;
	field bar: int = int:-7;
    }
}*/
