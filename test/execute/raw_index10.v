// @Harness: v2-exec
// @Test: set bit operator
// @Result: 0=3, 1=3, 2=7, 3=11, 4=19, 5=35, 6=67, 7=131

component raw_index10 {
    field f1: 8 = 0x03;

    method main(arg: int): 8 {
	f1[arg] = 0b1;
	return f1;
    }
}
