
component Sum {
    method main(a:int, b:int): int {
	local sum = 0;
	local cntr = 0;
	sum = a+ b;
	return sum;
    }

    method toInt(a: char[]): int {
        local accum = 0;
        local cntr = 0;
        local zero = '0'::int;

        for ( ; cntr < a.length; cntr++ ) {
           local dig = a[cntr]::int;
           accum = accum * 10 + dig - zero; 
        }

        return accum;
     }
}
