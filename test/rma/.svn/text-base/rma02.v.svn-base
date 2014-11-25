program rma02 {
    entrypoint main = Main.entry;
}

component Main {
    field f: A = new A(m1);
    field g: A = new A(m2);

    method entry() {
	while ( true ) { 
	    f = f.m();
	    f.func();
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
