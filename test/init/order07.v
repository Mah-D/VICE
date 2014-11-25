// @Harness: v2-init
// @Test: arithmetic operators
// @Result: PASS

class order07_a {
    field f: int;
    field g: int;
    constructor(a: int, b: int) {
        f = a;
        g = b;
    }
}

component order07 {
    field order: int[] = { 0, 0 };
    field pos: int = 0;

    field res_01: order07_a = new order07_a(first(6), second(7));

    method first(a: int): int {
        order[pos] = 1;
        pos++;
        return a;
    }

    method second(a: int): int {
        order[pos] = 2;
        pos++;
        return a;
    }
}

/* 
heap {
    record #0:10:order07 {
        field order: int[] = #1:int[2];
        field pos: int = int:2;
        field res_01: int = #2:order07_a;
    }
    record #1:2:int[2] {
	field 0:int = int:1;
	field 1:int = int:2;
    }
    record #2:2:order07_a {
	field f:int = int:6;
        field g:int = int:7;
    }
} */
