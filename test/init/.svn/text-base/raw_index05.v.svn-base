// @Harness: v2-init
// @Test: initialization interpreter > raw types > and operator
// @Result: PASS

component raw_index05 {
    field f: 8[] = { 0xf0, 0xf0, 0xf0, 0xf0 };

    constructor() {
	f[0][-1] |= 0b1;
	f[1][3]  |= 0b1;
	f[2][7]  |= 0b1;
	f[3][9]  |= 0b1;
    }
}

/*
heap {
    record #0:1:raw_index05 {
	field f: 8[] = #1:8[4];
    }
    record #1:4:8[4] {
	field 0: 8 = raw.8:0xf0;
	field 1: 8 = raw.8:0xf8;
	field 2: 8 = raw.8:0xf0;
	field 3: 8 = raw.8:0xf0;
    }
}
*/
