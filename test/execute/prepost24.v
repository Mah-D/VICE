// @Harness: v2-exec
// @Test: pre/post increment operations
// @Result: 0=1, 1=2, 2=3, 3=4, 4=5

component prepost24 {
    method main(arg: int): int {
	return ++arg;
    }
}
