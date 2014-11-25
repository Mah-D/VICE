// @Harness: v2-exec
// @Test: compound assignment operators
// @Result: 0=11, 1=13, 2=9, 3=22, 4=5, 5=3, 6=11

component cmpassn21 {

    field foo: int[] = { 11, 42 };

    method main(arg: int): int {
	local ind = 0;
	if ( arg == 1 ) foo[ind++] += 2;
	if ( arg == 2 ) foo[ind++] -= 2;
	if ( arg == 3 ) foo[ind++] *= 2;
	if ( arg == 4 ) foo[ind++] /= 2;
	if ( arg == 5 ) foo[ind++] %= 4;
	return foo[0];
    }
}
