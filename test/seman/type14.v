// @Harness: v2-seman
// @Test: typechecking; new operator
// @Result: ExpectedObjectType @ 6:9

component type14 {
    
    method testm() {
        new type14();
    }
}
