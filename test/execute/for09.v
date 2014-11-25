// @Harness: v2-exec
// @Test: if statements and ternary expressions
// @Result: 1=1, 2=3, 3=6, 10=55, 13=91

component for09 {
    
    method main(max: int): int {
	local i: int, cumul: int;
        for ( i = 1, cumul = 0; ; cumul += i, i++ ) {
	    if ( i > max ) break;
	    else continue;
	}
        return cumul;
    }
}
