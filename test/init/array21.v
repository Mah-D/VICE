// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component array21 {
    field a_01: char[] =  {'1', '2'};
    field a_02: char[] =  "hihihi";
    field len1: int = length(a_01);
    field len2: int = length(a_02);

    method length(a: char[]): int {
	return a.length;
    }
}

/* 
heap {
    record #0:4:array21 {
        field a_01: char[] = #1:char[2];
        field a_02: char[] = #2:char[6];
	field len1: int = int:2;
	field len2: int = int:6;
    }
    record #1:2:char[2] {
        field 0:char = char:49;
        field 1:char = char:50;
    }
    record #2:6:char[6] {
        field 0:char = char:104;
        field 1:char = char:105;
        field 2:char = char:104;
        field 3:char = char:105;
        field 4:char = char:104;
        field 5:char = char:105;
    }
} */
