// @Harness: v2-init
// @Test: compile-time constants for primitive types
// @Result: PASS

component const_bool01 {
    field a: bool = true;
    field b: bool = false;
}

/* 
heap {
    record #0:2:const_bool01 {
        field a: bool = bool:true;
        field b: bool = bool:false;
    }
} */
