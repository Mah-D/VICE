// @Harness: v2-seman
// @Result: PASS

class List<X> {
    method add(x: X) {
    }
}

component mp_list07 {
    method test() {
	local x: List<int> = makeList(0);
    }
    method makeList<T>(a: T): List<T> {
	return new List<T>();
    }
}
