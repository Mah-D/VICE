// @Harness: v2-init
// @Test: TypeCast operator
// @Result: TypeCheckException @ 16:31

class cast05_a {
}

class cast05_b extends cast05_a {
}

class cast05_c extends cast05_a {
}

component cast05 {
    field foo: cast05_a = new cast05_c();
    field bar: cast05_b = foo :: cast05_b;
}
