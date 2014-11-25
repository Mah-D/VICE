// @Harness: v2-seman
// @Test: global type resolution
// @Result: UnresolvedType @ 7:40

class type_res17 {
    field bar: type_res17;
    field foo: type_res17 = new unknown[0];
}
