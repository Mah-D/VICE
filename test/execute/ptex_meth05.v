// @Harness: v2-exec
// @Result: 0=42, 1=11, 2=12, 3=13, 4=14, 5=15, 6=42

component ptex_meth05 {

    method main(arg: int): int {
    	if ( arg == 1 ) return m(11, 'c');
    	if ( arg == 2 ) return m(12, false);
    	if ( arg == 3 ) return m(n3(13), 0);
    	if ( arg == 4 ) return m(n3(14), 0xff);
    	if ( arg == 5 ) return m(n3(15), n3(o));
        return 42;
    }

    method m<T, U>(x: T, y: U): T { return n(x); }
    method n<T>(x: T): T { return x; }
    method n3<T>(x: T): T { return n(n(n(x))); }
    method o() { }
}
