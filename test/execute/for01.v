// @Harness: v2-exec
// @Test: if statements and ternary expressions
// @Result: 1=1, 2=3, 3=6, 10=55, 13=91

component for01 {
    
    method main(max: int): int {
	local i: int, cumul = 0;
        for ( i = 1; i <= max; i++ ) cumul += i;
        return cumul;
    }
}
