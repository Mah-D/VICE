// @Harness: v2-init
// @Test: TypeCast operator
// @Result: TypeCheckException @ 13:31

class cast03_a {
}

class cast03_b extends cast03_a {
}

component cast03 {
    field foo: cast03_a = new cast03_a();
    field bar: cast03_b = foo :: cast03_b;
}
