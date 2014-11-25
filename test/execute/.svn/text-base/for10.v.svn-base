// @Harness: v2-exec
// @Test: if statements and ternary expressions
// @Result: 1=1, 2=3, 3=6, 10=55, 13=91

component for10 {
    
    method main(max: int): int {
	local i: int, cumul = 0, loop: int;
        for ( loop = 1; loop < 2; loop++ ) {
            for ( i = 1; ; cumul += i, i++ ) {
	        if ( i > max ) break;
	        else continue;
	    }
	}
        return cumul;
    }
}
