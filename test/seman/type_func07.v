// @Harness: v2-seman
// @Test: function () -> int
// @Result: TypeMismatch @ 7:21

class type_func07 {
    method testm(b: function(int, bool): int): int { 
	return b(0, 0); 
    }
}
