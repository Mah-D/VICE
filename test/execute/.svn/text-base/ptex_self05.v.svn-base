// @Harness: v2-exec
// @Result: 0=42, 1=11, 2=21, 3=48, 4=97, 5=42

class Self<T> {
    field self: T;
}

class I extends Self<I> {
    field val: int;
    constructor(v: int) {
        self = this;
        val = v;
    }
}

class C extends Self<C> {
    field val: char;
    constructor(v: char) {
        self = this;
        val = v;
    }
}

component ptex_self05 {
    field f: I = new I(11);
    field g: I = new I(21);
    field h: C = new C('0');
    field j: C = new C('a');

    method main(arg: int): int {
	if ( arg == 1 ) return f.self.val;
	if ( arg == 2 ) return g.val;
	if ( arg == 3 ) return h.self.val;
	if ( arg == 4 ) return j.val;
	return 42;
    }
}
