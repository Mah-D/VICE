// @Harness: v2-seman
// @Test: global type resolution
// @Result: UnresolvedType @ 10:17

component type_res11a {
    field g: int = type_res11b.m();
}

component type_res11b {
    method m(): unknown;
}
