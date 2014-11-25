// @Harness: v2-exec
// @Result: 0=42, 1=11, 2=12, 3=99, 4=79, 5=42

component ptex_meth09 {

    method main(arg: int): int {
        local f: function(int): int = id;
        local g: function(8): 8 = id;
        local h: function(char): char = id;

    	if ( arg == 1 ) return apply(11, f);
    	if ( arg == 2 ) return apply(12, f);
    	if ( arg == 3 ) return apply(id('c'), id(h)) :: int;
    	if ( arg == 4 ) return apply(id3(0x4f), id3(g)) :: int;
        return 42;
    }

    method apply<T>(x: T, f: function(T): T): T { 
	return f(x);
    }

    method id<T>(x: T): T { return x; }
    method id3<T>(x: T): T { return id(id(id(x))); }
}
