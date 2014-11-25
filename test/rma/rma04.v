program rma04 {
    entrypoint main = Main.entry;
}

component Main {
    field a: A = new A(new A(new A(null)));

    method entry() {
	while ( true ) { 
	    a = a.m();
        }
    }
}

class A {
    field other: A;
    constructor(o: A) { other = o; }
    method m(): A { return this; }
}

