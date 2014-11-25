program rma_array01 {
    entrypoint main = Main.entry;
}

component Main {
    field f: function()[] = { m1, m2 };
    field g: function()[] = { m3 };

    method entry() {
	while ( true ) { 
	    f[0]();
        }
    }

    method m1() { }
    method m2() { }
    method m3() { }
}
