// @Harness: v2-init
// @Test: initialization interpreter > raw types > and operator
// @Result: PASS

component raw_index10 {
    field f1: 8 = 0xf0;
    field f2: 8 = 0xf0;
    field f3: 8 = 0xf0;
    field f4: 8 = 0xf0;

    field b1: 8;
    field b2: 8;
    field b3: 8;
    field b4: 8;

    constructor() {
	b1 = f1[-1] = 0b1;
	b2 = f2[3]  = 0b1;
	b3 = f3[7]  = 0b0;
	b4 = f4[9]  = 0b1;
    }
}

/*
heap {
    record #0:8:raw_index10 {
	field f1: 8 = raw.8:0xf0;
	field f2: 8 = raw.8:0xf8;
	field f3: 8 = raw.8:0x70;
	field f4: 8 = raw.8:0x0f0;
	field b1: 1 = raw.1:0x1;
	field b2: 1 = raw.1:0x1;
	field b3: 1 = raw.1:0x0;
	field b4: 1 = raw.1:0x1;
    }
}
*/
