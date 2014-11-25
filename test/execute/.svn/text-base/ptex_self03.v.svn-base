// @Harness: v2-exec
// @Result: 0=42, 1=11, 2=21, 3=97, 4=42

class Self<T> {
}

class Myself<T> extends Self<Self<Myself<T> > > {
    field val: T;
    constructor(v: T) {
        val = v;
    }
}

component ptex_self03 {
    field f: Myself<int> = new Myself<int>(11);
    field g: Myself<int> = new Myself<int>(21);
    field h: Myself<char> = new Myself<char>('a');

    method main(arg: int): int {
	if ( arg == 1 ) return f.val;
	if ( arg == 2 ) return g.val;
	if ( arg == 3 ) return h.val;
	return 42;
    }
}
