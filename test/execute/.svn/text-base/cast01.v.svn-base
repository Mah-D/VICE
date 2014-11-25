// @Harness: v2-exec
// @Test: operation of instanceof construct
// @Result: 0=42, 1=42, 2=11, 3=42

class cast01_obj {
    field f: int = 11;
}

component cast01 {
    field foo: cast01_obj;
    field bar: cast01_obj = new cast01_obj();
    
    method main(arg: int): int {
	local o: cast01_obj = null;
	if ( arg == 1 ) o = (foo :: cast01_obj);
	if ( arg == 2 ) o = (bar :: cast01_obj);
	return o == null ? 42 : o.f;
    }
}
