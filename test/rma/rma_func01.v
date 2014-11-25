program rma_func01 {
    entrypoint main = Main.entry;
}

component Main {
    field f: function() = m1;
    field g: function() = m2;

    method entry() {
	while ( true ) { 
	    f();
        }
    }

    method m1() { }
    method m2() { }
}
