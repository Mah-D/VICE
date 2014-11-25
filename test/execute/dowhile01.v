// @Harness: v2-exec
// @Test: if statements and ternary expressions
// @Result: 1=1, 2=3, 3=6, 10=55, 13=91

component dowhile01 {
    
    method main(max: int): int {
	local i = 1, cumul = 0;
        do {
	    cumul += i;
	    i++;
	} while ( i <= max );
        return cumul;
    }
}
