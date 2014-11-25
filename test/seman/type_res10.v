// @Harness: v2-seman
// @Test: global type resolution
// @Result: UnresolvedType @ 6:5

component type_res10a {
    field g: int = type_res10b.f.x;
}

component type_res10b {
    field f: unknown;
}
