// @Harness: v2-seman
// @Result: PASS

class List<X> {
    method add(x: X) {
    }
}

component mp_list04 {
    method test() {
	local x = makeList(0, 0);
    }
    method makeList<X>(a: X, b: X): List<X> {
	return new List<X>();
    }
}
