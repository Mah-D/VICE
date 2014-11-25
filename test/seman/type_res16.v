// @Harness: v2-seman
// @Test: global type resolution
// @Result: UnresolvedType @ 7:40

class type_res16 {
    field bar: type_res16;
    field foo: type_res16 = new unknown();
}
