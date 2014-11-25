// @Harness: v2-exec
// @Test: if statements and ternary expressions
// @Result: 1=1, 2=3, 3=6, 10=55, 13=91

component while06 {

    method main(max: int): int {
	local i = 1, cumul = 0;
        while ( check(cumul += i, ++i, max) ) ;
        return cumul;
    }

    method check(c: int, a: int, m: int): bool {
	return a <= m;
    }
}
