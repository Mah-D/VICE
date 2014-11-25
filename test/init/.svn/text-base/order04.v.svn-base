// @Harness: v2-init
// @Test: arithmetic operators
// @Result: PASS

class order04_a {
    field foo: int;
}

component order04 {
    field order: int[] = { 0, 0 };
    field pos: int = 0;

    field res_01: int = first().foo = second(3);

    method first(): order04_a {
        order[pos] = 1;
        pos++;
        return new order04_a();
    }

    method second(a: int): int {
        order[pos] = 2;
        pos++;
        return a;
    }
}

/* 
heap {
    record #0:10:order04 {
        field order: int[] = #1:int[2];
        field pos: int = int:2;
        field res_01: int = int:3;
    }
    record #1:2:int[2] {
	field 0:int = int:1;
	field 1:int = int:2;
    }
} */
