// @Harness: v2-exec
// @Test: if statements and ternary expressions
// @Result: 100

component if02b {
    
    // choose(true,  241, 100) => 241
    // choose(false, 241, 100) => 100
    method main(arg: int): int {
	return choose(false, 241, 100);
    }

    method choose(c: bool, a: int, b: int): int {
	if ( c ) return a;
        return b;
    }
}
