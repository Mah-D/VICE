// @Harness: v2-seman
// @Test: typechecking; new operator
// @Result: TypeMismatch @ 14:15

class type62_obj {
    method foo(): int {
	return 0;
    }
}

component type62 {
    
    method testm(): bool {
        return new type62_obj().foo();
    }
}
