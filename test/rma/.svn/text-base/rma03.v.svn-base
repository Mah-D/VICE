program rma03 {
    entrypoint main = Main.entry;
}

component Main {
    field a: A = new A();
    field b: A = new B();
    field f: function(): A = m1;
    field g: function(): A = m2;

    method entry() {
	while ( true ) { 
	    a = f();
        }
    }

    method m1(): A { return a; }
    method m2(): A { return b; }
}

class A {
    method m(): A { return this; }
}

class B extends A {
    method m(): A { return Main.b; }
}
