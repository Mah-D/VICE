// @Harness: v2-seman
// @Test: typechecking; primitive operations
// @Result: CannotCompareValues @ 6:16

class type43 {
    
    method testm() {
        local a = (2 != null);
    }
}
