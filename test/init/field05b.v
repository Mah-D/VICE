// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

class field05b_obj {
    field bar: char[] = "hello";
}

component field05b {
    field foo: field05b_obj = new field05b_obj();
}

/* 
heap {
    record #0:1:field05b {
        field foo: field05b_obj = #1:field05b_obj;
    }
    record #1:1:field05b_obj {
        field bar: char[] = #2:char[5];
    }
    record #2:5:char[5] {
        field 0: char = char:104;
        field 1: char = char:101;
        field 2: char = char:108;
        field 3: char = char:108;
        field 4: char = char:111;
    }
} */
