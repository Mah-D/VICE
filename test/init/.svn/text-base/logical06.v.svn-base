// @Harness: v2-init
// @Test: logical operators
// @Result: PASS

class ander {
    field a: bool; // true if A() evaluated
    field b: bool; // true if B() evaluated
    field R: bool;

    method AND(v1: bool, v2: bool) {
	R = A(v1) and B(v2);
    }

    method A(v: bool): bool {
	a = true;
	return v;
    }

    method B(v: bool): bool {
	b = true;
	return v;
    }
}

component logical06 {
    field res_01: ander = new ander();
    field res_02: ander = new ander();
    field res_03: ander = new ander();
    field res_04: ander = new ander();

    constructor() {
	res_01.AND(true, false);
	res_02.AND(true, true);
	res_03.AND(false, true);
	res_04.AND(false, false);
    }

}

/* 
heap {
    record #0:4:logical06 {
	field res_01: ander = #1:ander;
	field res_02: ander = #2:ander;
	field res_03: ander = #3:ander;
	field res_04: ander = #4:ander;
    }
    record #1:3: ander {
	field a: bool = bool:true;
	field b: bool = bool:true;
	field R: bool = bool:false;
    }
    record #2:3: ander {
	field a: bool = bool:true;
	field b: bool = bool:true;
	field R: bool = bool:true;
    }
    record #3:3: ander {
	field a: bool = bool:true;
	field b: bool = bool:false;
	field R: bool = bool:false;
    }
    record #4:3: ander {
	field a: bool = bool:true;
	field b: bool = bool:false;
	field R: bool = bool:false;
    }
} */
