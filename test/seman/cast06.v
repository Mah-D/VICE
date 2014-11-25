// @Harness: v2-seman
// @Test: TypeCast operation
// @Result: PASS

class cast06_a {
    field foo: cast06_a;
    field bar: cast06_b = foo :: cast06_b;
}

class cast06_b extends cast06_a {
}
