// @Harness: v2-seman
// @Test: global type resolution
// @Result: UnresolvedType @ 7:40

class type_res15 {
    field bar: type_res15;
    field foo: bool = bar instanceof unknown;
}
