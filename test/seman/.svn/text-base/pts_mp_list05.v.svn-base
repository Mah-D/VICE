// @Harness: v2-seman
// @Result: PASS

class List<X> {
    method add(x: X) {
    }
}

component mp_list05 {
    method test() {
	local x = makeList(0);
    }
    method makeList<T>(a: T): List<T> {
	return new List<T>();
    }
}
