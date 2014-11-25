// @Harness: v2-seman
// @Test: unreachable code
// @Result: UnreachableCode @ 9:10

class unreach10 {
    
    method testm() {
        while ( true ) {
            if ( true ) break;
            else break;
            {
                local foo: int = 0;
            }
        }
    }
}
