// @Harness: v2-exec
// @Test: static method invocations
// @Main: fold02.main
// @Result: 0=2, 1=8, 2=17, 3=29, 4=44, 5=62, 6=83, 7=107, 8=134, 9=164

component fold02 {
    field array: int[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

    method main(arg: int): int {
	local a = fold(add, array, arg);
        local b = fold(add2, array, arg);
	return a + b;
    }

    method add(a: int, b: int): int {
	return a + b;
    }

    method add2(a: int, b: int): int {
        return a + 2 * b;
    }

    // recursive version of fold
    method fold(f: function(int, int): int, a: int[], m: int): int {
	return m == 0 ? a[0] : f(fold(f, a, m-1), a[m]);        
    }
}
