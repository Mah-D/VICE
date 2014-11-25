// @Harness: v2-seman
// @Result: CannotOverrideTypeParams @ 11:25

class mp_ovr03_a {
    method makeArray<X>(a: X): X[] {
	return new X[0];	
    }
}

class mp_ovr03_b extends mp_ovr03_a {
    method makeArray<Y, Z>(a: Y): Y[] {
	return a;	
    }
}
