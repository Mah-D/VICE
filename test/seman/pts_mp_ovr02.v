// @Harness: v2-seman
// @Result: CannotOverrideReturnType @ 11:32

class mp_ovr02_a {
    method makeArray<X>(a: X): X[] {
	return new X[0];	
    }
}

class mp_ovr02_b extends mp_ovr02_a {
    method makeArray<Y>(a: Y): Y {
	return a;	
    }
}
