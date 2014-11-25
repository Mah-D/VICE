// @Harness: v2-exec
// @Test: operation of instanceof construct
// @Result: 0=42, 1=21, 2=32, 3=42

class instof01_obj {
}

component instof01 {
    field foo: instof01_obj;
    
    method main(arg: int): int {
	if ( arg == 1 ) return foo instanceof instof01_obj ? 11 : 21;
	if ( arg == 2 ) return foo instanceof instof01_obj ? 31 : 32;
	return 42;
    }
}
