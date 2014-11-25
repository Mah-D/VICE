// @Harness: v2-exec
// @Test: compound assignment operators
// @Result: 0=11, 1=13, 2=9, 3=22, 4=5, 5=3, 6=11

component cmpassn20 {

    field foo: int[] = { 11, 42 };

    method main(arg: int): int {
	if ( arg == 1 ) foo[0] += 2;
	if ( arg == 2 ) foo[0] -= 2;
	if ( arg == 3 ) foo[0] *= 2;
	if ( arg == 4 ) foo[0] /= 2;
	if ( arg == 5 ) foo[0] %= 4;
	return foo[0];
    }
}
