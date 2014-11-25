// @Harness: v2-exec
// @Result: 0=42, 1=11, 2=21, 3=31, 4=42

class Self<T> {
}

class Myself extends Self<Myself> {
    field val: int;
    constructor(v: int) {
        val = v;
    }
}

component ptex_self01 {
    field f: Myself = new Myself(11);
    field g: Myself = new Myself(21);
    field h: Myself = new Myself(31);

    method main(arg: int): int {
	if ( arg == 1 ) return f.val;
	if ( arg == 2 ) return g.val;
	if ( arg == 3 ) return h.val;
	return 42;
    }
}
