// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component array23 {
    field a_01: int[] =  a_int();
    field a_02: bool[] =  a_bool();
    field a_03: char[] = a_char();
    field a_04: char[] = a_str();

    method a_int(): int[] {
	local a: int[] = {};
        return a;
    }

    method a_bool(): bool[] {
	local a: bool[] = {};
        return a;
    }

    method a_char(): char[] {
	local a: char[] = {};
        return a;
    }

    method a_str(): char[] {
        return "";
    }

}

/* 
heap {
    record #0:4:array23 {
        field a_01: int[] = #1:int[0];
        field a_02: bool[] = #2:bool[0];
        field a_03: char[] = #3:char[0];
        field a_04: char[] = #4:char[0];
    }
    record #1:0:int[0] {
    }
    record #2:0:bool[0] {
    }
    record #3:0:char[0] {
    }
    record #4:0:char[0] {
    }
} */
