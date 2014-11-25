// @Harness: v2-exec
// @Result: 0=0; 1=1

component pt_recurse03 {
	method main(arg: int): int {
		return C.m(0);
	}
}

class L<T> {
}

component C {
	method m<T>(x: T): T {
		if (false) m(new L<T>());
		return x;
	}
}