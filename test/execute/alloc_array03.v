// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=49, 2=50, 3=104, 4=101, 5=104, 6=42

component alloc_array03 {
    field a: char[] = array1();
    field b: char[] = array2();

    method array1(): char[] {
	local arr = {'1','2'};
        return arr;
    }
    method array2(): char[] {
        return "heh";
    }


    method main(arg: int): char {
	if ( arg == 1 ) return a[0];
	if ( arg == 2 ) return a[1];
	if ( arg == 3 ) return b[0];
	if ( arg == 4 ) return b[1];
	if ( arg == 5 ) return b[2];
	return '*';
    }
}
