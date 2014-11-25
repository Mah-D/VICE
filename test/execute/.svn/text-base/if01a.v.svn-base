// @Harness: v2-exec
// @Test: if statements and ternary expressions
// @Result: 241

component if01a {
    
    // choose(true,  241, 100) => 241
    // choose(false, 241, 100) => 100

    method main(arg: int): int {
	return choose(true, 241, 100);
    }

    method choose(c: bool, a: int, b: int): int {
	return c ? a : b;
    }
}
