// @Harness: v2-init
// @Test: initialization interpreter > raw types > and operator
// @Result: PASS

component raw_index11 {

    field b1: 8;
    field b2: 8;
    field b3: 8;
    field b4: 8;

    constructor() {
    	local f1: 8 = 0xf0;
    	local f2: 8 = 0xf0;
    	local f3: 8 = 0xf0;
    	local f4: 8 = 0xf0;

	b1 = f1[-1] = 0b1;
	b2 = f2[3]  = 0b1;
	b3 = f3[7]  = 0b0;
	b4 = f4[9]  = 0b1;
    }
}

/*
heap {
    record #0:4:raw_index11 {
	field b1: 1 = raw.1:0x1;
	field b2: 1 = raw.1:0x1;
	field b3: 1 = raw.1:0x0;
	field b4: 1 = raw.1:0x1;
    }
}
*/
