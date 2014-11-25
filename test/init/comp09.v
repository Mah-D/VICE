// @Harness: v2-init
// @Test: character comparison operators
// @Result: PASS

component comp09 {
    field res_01: bool = op('a', 'b');
    field res_02: bool = op('b', 'a');
    field res_03: bool = op('\n', '1');
    field res_04: bool = op('\377', '0');
    field res_05: bool = op('\n', '\n');
    field res_06: bool = op('\377', '\377');
    field res_07: bool = op('a', 'A');
    field res_08: bool = op('A', 'Z');
    field res_09: bool = op('$', 'z');
    field res_10: bool = op('z', '~');

    method op(a: char, b: char): bool {
	return a >= b;
    }
}

/* 
heap {
    record #0:10:comp09 {
        field res_01: bool = bool:false;
        field res_02: bool = bool:true;
        field res_03: bool = bool:false;
        field res_04: bool = bool:true;
        field res_05: bool = bool:true;
        field res_06: bool = bool:true;
        field res_07: bool = bool:true;
        field res_08: bool = bool:false;
        field res_09: bool = bool:false;
        field res_10: bool = bool:false;
    }
} */
