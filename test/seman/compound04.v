// @Harness: v2-seman
// @@Test: "@Test variable initialization in compound assignment"
// @Result: PASS

class compound04 {

    method testm() {
        local cntr = 0;
        cntr = cntr += 2;
    }
}
