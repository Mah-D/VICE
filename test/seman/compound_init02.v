// @Harness: v2-seman
// @@Test: "@Test variable initialization in compound assignment"
// @Result: VariableNotInitialized @ 7:14

class compound_init02 {

    method testm() {
        local array: int[];
        array[0] += 2;
    }
}
