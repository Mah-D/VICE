// @Harness: v2-exec
// @Result: 0=42, 1=17, 2=18, 3=99, 4=79, 5=42

component ptex_meth10 {

    method main(arg: int): int {
        local g: function(8): 8 = id;
        local h: function(char): char = id;

    	if ( arg == 1 ) return apply(11, add6);
    	if ( arg == 2 ) return apply(12, add6);
    	if ( arg == 3 ) return apply(id('c'), id(h)) :: int;
    	if ( arg == 4 ) return apply(id3(0x4f), id3(g)) :: int;
        return 42;
    }

    method apply<T>(x: T, f: function(T): T): T { 
	return f(x);
    }

    method id<T>(x: T): T { return x; }
    method id3<T>(x: T): T { return id(id(id(x))); }
    method add6(x: int): int { return x + 6; }
}
