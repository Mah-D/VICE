// @Harness: v2-seman
// @Test: unreachable code
// @Result: UnreachableCode @ 9:9

class unreach16 {
    
    method testm() {
        switch ( 0 ) {
            default {
                return;
                testm();
            }
        }
    }
}
