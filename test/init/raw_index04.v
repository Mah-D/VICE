// @Harness: v2-init
// @Test: initialization interpreter > raw types > and operator
// @Result: PASS

component raw_index04 {
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
	local x1 = f1, 
	      x2 = f2, 
	      x3 = f3, 
              x4 = f4, 
              x5 = f5, 
              x6 = f6, 
              x7 = f7, 
              x8 = f8, 
              x9 = f9;

	x1[-1] |= 0b1;
	x2[3]  |= 0b1;
	x3[7]  |= 0b1;
	x4[9]  |= 0b1;
	x5[15] |= 0b1;
	x6[17] |= 0b1;
	x7[0]  |= 0b1;
	x8[31] |= 0b1;
	x9[32] |= 0b1;

	f1 = x1;
	f2 = x2;
	f3 = x3;
	f4 = x4;
	f5 = x5;
	f6 = x6;
	f7 = x7;
	f8 = x8;
	f9 = x9;
    }
}

/*
heap {
    record #0:1:raw_index04 {
	field f1: 8 = raw.8:0xf0;
	field f2: 8 = raw.8:0xf8;
	field f3: 8 = raw.8:0xf0;
	field f4: 8 = raw.8:0x0f0;
	field f5: 16 = raw.16:0xbff0;
	field f6: 16 = raw.16:0x3ff0;
	field f7: 32 = raw.32:0x3ff0eeef;
	field f8: 32 = raw.32:0xbff0eeee;
	field f9: 32 = raw.32:0x3ff0eeee;
    }
}
*/
