// @Harness: v2-exec
// @Test: if statements and ternary expressions
// @Result: 1=0, 2=1, 3=3, 4=3, 5=4, 6=6, 7=6, 8=BoundsCheckException

class A { 
    field f: int;
    field t: A = this;
    constructor(i: int) { f = i; }
}

component sum01 {

    field arr: A[] = build(7);

    method build(len: int): A[] {
        local a = new A[len];
	local cntr = 0;
	for ( ; cntr < len; cntr++ ) {
	    a[cntr] = new A(cntr % 3);
	}
	return a;
    }
    
    method main(max: int): int {
	local i: int, cumul = 0;
        for ( i = 0; i < max; i++ ) cumul += arr[i].t.f;
        return cumul;
    }
}
