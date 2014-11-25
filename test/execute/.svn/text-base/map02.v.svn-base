// @Harness: v2-exec
// @Test: static method invocations
// @Main: map02.main
// @Result: 0=2, 1=5, 2=9, 3=14, 4=20, 5=27, 6=35, 7=44, 8=54, 9=65

component map02 {
    field a: int[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
    field b: int[] = new int[10]; 

    method main(arg: int): int {
	map(a, inc, b, arg);
	return sum(b);
    }

    method inc(a: int): int {
	return a + 1;
    }

    // recursive version of map
    method map(a: int[], f: function(int): int, res: int[], m: int) {
        if ( m > 0 ) map(a, f, res, m-1);
        res[m] = f(a[m]);
    }

    method sum(array: int[]): int {
	local i = 0, cumul = 0;
        for ( ; i < array.length; i++ ) cumul += array[i];
	return cumul;
    }
}
