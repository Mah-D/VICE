// @Harness: v2-exec
// @Test: dynamically allocated memory exceptions
// @Result: 0=42, 1=AllocationException, 2=AllocationException, 3=AllocationException, 4=42

component rtex_alloc01 {

    method foo(): int[] {
	local x = { 11, 12, 13, 14 };
	return x;
    }

    method main(arg: int): int {
	if ( arg == 1) return foo()[0];
	if ( arg == 2) return foo()[1];
	if ( arg == 3) return foo()[2];
	return 42;
    }
}
