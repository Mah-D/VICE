program rma_down01 {
    entrypoint main = Main.entry;
}

component Main {
    field f: A = new A();
    field g: A = new B();

    method entry() {
	while ( true ) g = g.m();
    }
}

class A {
    field other: A;
    method m(): A { return this; }
}

class B extends A {
    constructor() { other = this; }
    method m(): A { return other; }
}
