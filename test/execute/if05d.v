// @Harness: v2-exec
// @Test: if statements and ternary expressions
// @Result: 0

class ch05d {
    field A: int;
    field B: int;
    field R: int;

    method choose(c: bool, a: int, b: int) {
	if ( c ) R = A = a;
	else R = B = b;
    }
}

component if05d {
    
    field res_01: ch05d = new ch05d();
    field res_02: ch05d = new ch05d();

    // res_01.A = 160;
    // res_01.B = 0;
    // res_01.R = 160;
    // res_02.A = 0;
    // res_02.B = 24;
    // res_02.R = 24;

    method main(arg: int): int {
	res_01.choose(true, 160, 42);
        res_02.choose(false, 100, 24);
	return res_02.A;
    }

}

