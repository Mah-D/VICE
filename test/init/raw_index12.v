// @Harness: v2-init
// @Test: initialization interpreter > raw types > and operator
// @Result: PASS

component raw_index12 {
    field f: 8[] = { 0xf0, 0xf0, 0xf0, 0xf0 };

    constructor() {
	local x = 0;
	f[x++][-1] |= 0b1;
	f[x++][3]  |= 0b1;
	f[x++][7]  |= 0b1;
	f[x++][9]  |= 0b1;
    }
}

/*
heap {
    record #0:1:raw_index12 {
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
