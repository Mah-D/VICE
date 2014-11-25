// @Harness: v2-seman
// @@Test: "@Test variable initialization in compound assignment"
// @Result: VariableNotInitialized @ 7:10

class compound_init03 {

    method testm() {
        local cntr: int;
        cntr += cntr;
    }
}
