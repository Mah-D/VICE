// @Harness: v2-exec
// @Test: if statements and ternary expressions
// @Result: 1=1, 2=3, 3=6, 10=55, 13=91

component while07 {
    
    method main(max: int): int {
	local i = 1, cumul = 0;
        while ( true ) {
	    cumul += i++;
	    if ( i <= max ) continue;
	    break;
	}
        return cumul;
    }
}
