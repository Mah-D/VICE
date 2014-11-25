// @Harness: v2-seman
// @Test: global type resolution
// @Result: UnresolvedType @ 7:40

class type_res19 {
    method foo(): type_res16 {
	return new unknown();
    }
}
