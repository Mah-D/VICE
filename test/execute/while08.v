// @Harness: v2-exec
// @Test: while statements
// @Result: 1=1, 2=3, 3=6, 10=55, 13=91

component while08 {
    
    method main(max: int): int {
	local i = 1, cumul = 0, loop = 1;
        while ( loop++ < 2 ) {
            for ( i = 1; ; cumul += i, i++ ) {
	        if ( i > max ) break;
	        else continue;
	    }
	}
        return cumul;
    }
}
