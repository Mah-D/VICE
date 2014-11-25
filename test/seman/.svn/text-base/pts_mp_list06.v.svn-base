// @Harness: v2-seman
// @Result: PASS

class List<X> {
    method add(x: X) {
    }
}

component mp_list06 {
    method test() {
	local x = makeList(0, 0);
    }
    method makeList<T>(a: T, b: T): List<T> {
	return new List<T>();
    }
}
