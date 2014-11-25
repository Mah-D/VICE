// @Harness: v2-exec
// @Result: 0=42, 1=11, 2=21, 3=48, 4=97, 5=42

class Self<T> {
    field self: T;
}

class W<T> extends Self<W<T> > {
    field val: T;
    constructor(v: T) {
        self = this;
        val = v;
    }
}

component ptex_self04 {
    field f: W<int> = new W<int>(11);
    field g: W<int> = new W<int>(21);
    field h: W<char> = new W<char>('0');
    field j: W<char> = new W<char>('a');

    method main(arg: int): int {
	if ( arg == 1 ) return f.self.val;
	if ( arg == 2 ) return g.val;
	if ( arg == 3 ) return h.self.val;
	if ( arg == 4 ) return j.val;
	return 42;
    }
}
