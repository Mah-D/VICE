program rma01 {
    entrypoint main = Main.entry;
}

component Main {
    field f: A = new A();
    field g: B = new B();

    method entry() {
	while ( true ) f = f.m();
    }
}

class A {
    method m(): A { return this; }
}

class B extends A {
    method m(): A { return Main.g; }
}
