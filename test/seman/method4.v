// @Harness: v2-seman
// @Test: attempting to assign to methods
// @Result: CannotAssignToMember @ 7:15

class method4 {
 
    method testm() {
        testm = testm;
    } 
}
