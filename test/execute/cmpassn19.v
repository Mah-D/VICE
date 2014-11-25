// @Harness: v2-exec
// @Test: compound assignment operators
// @Result: 0=11, 1=13, 2=9, 3=22, 4=5, 5=3, 6=11

component cmpassn19 {

    method main(arg: int): int {
	local foo = 11;
	if ( arg == 1 ) foo += 2;
	if ( arg == 2 ) foo -= 2;
	if ( arg == 3 ) foo *= 2;
	if ( arg == 4 ) foo /= 2;
	if ( arg == 5 ) foo %= 4;
	return foo;
    }
}
