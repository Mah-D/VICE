// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component array24 {
    field a_01: function(): int[] = f;

    method f(): int[] {
        return null;
    }
}

/* 
heap {
    record #0:1:array24 {
        field a_01: function():int[] = [#0:array24,array24:f()];
    }
} */
