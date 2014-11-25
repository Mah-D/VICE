// @Harness: v2-seman
// @Test: unreachable code
// @Result: UnreachableCode @ 9:10

class unreach22 {
    
    method testm() {
        while ( true ) {
            if ( true ) break;
            else return;
            {
                local foo: int = 0;
            }
        }
    }
}
