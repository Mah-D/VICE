// @Harness: v2-exec
// @Result: 0=42, 1=11, 2=12, 3=13, 4=14, 5=42

component ptex_meth01 {

    method main(arg: int): int {
    	if ( arg == 1 ) return m(11);
    	if ( arg == 2 ) return m(12);
    	if ( arg == 3 ) return m(13);
    	if ( arg == 4 ) return m(14);
        return 42;
    }

    method m<T>(x: T): T { return x; }
}
