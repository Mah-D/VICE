program rma_down01 {
    entrypoint main = Main.entry;
}

component Main {
    field f: A = new A();
    field g: B = new B();

    method entry() {
	while ( true ) {
	    f = g; 
	    g = move(g);
        }
    }
    method move(b: B): B {
	b.other = b;
	return b;
    }
}

class A {
    field other: A;
}

class B extends A {
}
