// @Harness: v2-seman
// @Result: PASS

class L<T> {
}

component C {
	method m<T>(x: T) {
		m(null :: (L<T>));
	}
}