// @Harness: v2-exec
// @Test: initialization interpreter > raw types > and operator
// @Result: 0=0, 4=1, 8=0, 12=1, 16=0, 20=1, 24=0, 28=1, 32=0, 36=1, 40=0, 44=1, 48=0, 52=1, 56=0, 60=1, 63=1

component raw_index04 {
    field foo: 64 = 0xf0f0f0f0f0f0f0f0;

    method main(arg: int): 1 {
	return foo[arg];
    }
}
