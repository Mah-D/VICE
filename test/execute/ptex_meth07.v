// @Harness: v2-exec
// @Result: 0=42, 1=11, 2=12, 3=13, 4=14, 5=42

component ptex_meth07 {

    method main(arg: int): int {
        local f: function(int): int = id;
    	if ( arg == 1 ) return apply(11, f);
    	if ( arg == 2 ) return apply(12, f);
    	if ( arg == 3 ) return apply(13, f);
    	if ( arg == 4 ) return apply(14, f);
        return 42;
    }

    method apply<T>(x: T, f: function(T): T): T { 
	return f(x);
    }

    method id<T>(x: T): T { return x; }
}
