// @Harness: v2-init
// @Test: logical operators
// @Result: PASS

class orer {
    field a: bool; // true if A() evaluated
    field b: bool; // true if B() evaluated
    field R: bool;

    method OR(v1: bool, v2: bool) {
	R = A(v1) or B(v2);
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

component logical07 {
    field res_01: orer = new orer();
    field res_02: orer = new orer();
    field res_03: orer = new orer();
    field res_04: orer = new orer();

    constructor() {
	res_01.OR(true, false);
	res_02.OR(true, true);
	res_03.OR(false, true);
	res_04.OR(false, false);
    }

}

/* 
heap {
    record #0:4:logical07 {
	field res_01: orer = #1:orer;
	field res_02: orer = #2:orer;
	field res_03: orer = #3:orer;
	field res_04: orer = #4:orer;
    }
    record #1:3: orer {
	field a: bool = bool:true;
	field b: bool = bool:false;
	field R: bool = bool:true;
    }
    record #2:3: orer {
	field a: bool = bool:true;
	field b: bool = bool:false;
	field R: bool = bool:true;
    }
    record #3:3: orer {
	field a: bool = bool:true;
	field b: bool = bool:true;
	field R: bool = bool:true;
    }
    record #4:3: orer {
	field a: bool = bool:true;
	field b: bool = bool:true;
	field R: bool = bool:false;
    }
} */
