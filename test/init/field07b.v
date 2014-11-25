// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

class field07b_obj {
    field bar: int[] = { 0, 1 };
}

component field07b {
    field foo: field07b_obj = new field07b_obj();
}

/* 
heap {
    record #0:1:field07b {
        field foo: field07b_obj = #1:field07b_obj;
    }
    record #1:1:field07b_obj {
        field bar: int[] = #2:int[2];
    }
    record #2:2:int[2] {
        field 0: int = int:0;
        field 1: int = int:1;
    }
} */
