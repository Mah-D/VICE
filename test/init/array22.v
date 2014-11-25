// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component array22 {
    field a_01: char[] =  fill(new char[4]);
    field a_02: char[] =  fill(new char[6]);

    method fill(a: char[]): char[] {
	local i = 0;
        for ( ; i < a.length; i++ ) a[i] = 'a';
	return a;
    }
}

/* 
heap {
    record #0:2:array22 {
        field a_01: char[] = #1:char[4];
        field a_02: char[] = #2:char[6];
    }
    record #1:4:char[4] {
        field 0:char = char:97;
        field 1:char = char:97;
        field 2:char = char:97;
        field 3:char = char:97;
    }
    record #2:6:char[6] {
        field 0:char = char:97;
        field 1:char = char:97;
        field 2:char = char:97;
        field 3:char = char:97;
        field 4:char = char:97;
        field 5:char = char:97;
    }
} */
