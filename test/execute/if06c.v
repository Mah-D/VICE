// @Harness: v2-exec
// @Test: if statements and ternary expressions
// @Result: 13

component if06c {
    
//    choose(0, 241, 100, 13) = 241 
//    choose(1, 241, 100, 13) = 100
//    choose(2, 241, 100, 13) = 13

    method main(arg: int): int {
	return choose(2, 241, 100, 13);
    }


    method choose(cond: int, a: int, b: int, c: int): int {
	if ( cond == 0 ) return a;
        else if ( cond == 1 ) return b;
        else return c;
    }
}
