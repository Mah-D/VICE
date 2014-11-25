// @Harness: v2-seman
// @Result: PASS

component mp_unify13 {

    method test() {
	local a: function(int) = null;
	local b: function(bool) = null;
	local x = id(0, a);
	local y = id(true, b);
    }
    method id<X>(x: X, y: function(X)): X {
	return x;	
    }
}
