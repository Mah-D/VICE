// @Harness: v2-exec
// @Test: if statements and ternary expressions
// @Result: 1=1, 2=3, 3=6, 10=55, 13=91

component dowhile08 {
    
    method main(max: int): int {
	local i = 1, cumul = 0, loop = 1;
        do {
            for ( i = 1; ; cumul += i, i++ ) {
	        if ( i > max ) break;
	        else continue;
	    }
	} while (++loop < 2);
        return cumul;
    }
}
