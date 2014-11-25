program rma_array01 {
    entrypoint main = Main.entry;
}

component Main {
    field f: A[] = { new A(m1) };
    field g: A[] = { new A(m2) };

    method entry() {
	while ( true ) { 
	    f[0] = f[0].m();
	    f[0].func();
        }
    }

    method m1() { }
    method m2() { }
}

class A {
    field func: function();
    constructor(f: function()) { func = f; }
    method m(): A { return this; }
}
