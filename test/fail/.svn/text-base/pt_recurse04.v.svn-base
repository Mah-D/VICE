// @Harness: v2-exec
// @Result: 0=0; 1=1

component pt_recurse04 {
	method main(arg: int): int {
		return C.m(0, false);
	}
}

class L<T> {
}

component C {
	method m<T>(x: T, y: bool): T {
		if (y) m(new L<T>(), y);
		return x;
	}
}