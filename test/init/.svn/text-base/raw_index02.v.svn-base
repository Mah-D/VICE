// @Harness: v2-init
// @Test: initialization interpreter > raw types > and operator
// @Result: PASS

component raw_index02 {
    field f1: 8 = 0xf0;
    field f2: 8 = 0xf0;
    field f3: 8 = 0xf0;
    field f4: 8 = 0xf0;
    field f5: 16 = 0x3ff0;
    field f6: 16 = 0x3ff0;
    field f7: 32 = 0x3ff0eeee;
    field f8: 32 = 0x3ff0eeee;
    field f9: 32 = 0x3ff0eeee;

    constructor() {
	f1[-1] = 0b1;
	f2[3]  = 0b1;
	f3[7]  = 0b0;
	f4[9]  = 0b1;
	f5[15] = 0b1;
	f6[17] = 0b1;
	f7[0]  = 0b1;
	f8[31] = 0b1;
	f9[32] = 0b1;
    }
}

/*
heap {
    record #0:1:raw_index02 {
	field f1: 8 = raw.8:0xf0;
	field f2: 8 = raw.8:0xf8;
	field f3: 8 = raw.8:0x70;
	field f4: 8 = raw.8:0x0f0;
	field f5: 16 = raw.16:0xbff0;
	field f6: 16 = raw.16:0x3ff0;
	field f7: 32 = raw.32:0x3ff0eeef;
	field f8: 32 = raw.32:0xbff0eeee;
	field f9: 32 = raw.32:0x3ff0eeee;
    }
}
*/
