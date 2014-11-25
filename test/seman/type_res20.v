// @Harness: v2-seman
// @Test: global type resolution
// @Result: UnresolvedType @ 7:40

class type_res20 {
    method foo(): type_res20 {
	return new unknown[0];
    }
}
