// @Harness: v2-exec
// @Result: 0=42, 1=11, 2=12, 3=13, 4=14, 5=15, 6=42

component ptex_meth06 {

    method main(arg: int): int {
    	if ( arg == 1 ) return m(11, 'c', 'd');
    	if ( arg == 2 ) return m(12, false, 'e');
    	if ( arg == 3 ) return m(n3(13), 0, false);
    	if ( arg == 4 ) return m(n3(14), 0xff, o);
    	if ( arg == 5 ) return m(n3(15), n3(o), p);
        return 42;
    }

    method m<T, U, V>(x: T, y: U, z: V): T { 
	local t = n(y);
	local u = n(z);
	return n(x); 
    }

    method n<T>(x: T): T { return x; }
    method n3<T>(x: T): T { return n(n(n(x))); }
    method o() { }
    method p(x: int) { }
}
