// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component array13 {
    field a_01: char[] =  array1();
    field a_02: char[] =  array1();
    field a_03: char[] =  array2();
    field a_04: char[] =  array2();

    method array1(): char[] {
	local arr = {'1','2'};
        return arr;
    }
    method array2(): char[] {
        return "hi";
    }
}

/* 
heap {
    record #0:4:array13 {
        field a_01: char[] = #1:char[2];
        field a_02: char[] = #2:char[2];
        field a_03: char[] = #3:char[2];
        field a_04: char[] = #4:char[2];
    }
    record #1:2:char[2] {
        field 0:char = char:49;
        field 1:char = char:50;
    }
    record #2:2:char[2] {
        field 0:char = char:49;
        field 1:char = char:50;
    }
    record #3:2:char[2] {
        field 0:char = char:104;
        field 1:char = char:105;
    }
    record #4:2:char[2] {
        field 0:char = char:104;
        field 1:char = char:105;
    }
} */
