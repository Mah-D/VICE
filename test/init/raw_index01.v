// @Harness: v2-init
// @Test: initialization interpreter > raw types > and operator
// @Result: PASS

component raw_index01 {
    field f1: 1 = 0xf0[-1];
    field f2: 1 = 0xf0[0];
    field f3: 1 = 0xf0[4];
    field f4: 1 = 0xf0[9];
    field f5: 1 = 0x3ff0[11];
    field f6: 1 = 0x3ff0[17];
    field f7: 1 = 0x3ff0eeee[1];
    field f8: 1 = 0x3ff0eeee[24];
    field f9: 1 = 0x3ff0eeee[32];
}

/*
heap {
    record #0:1:raw_index01 {
	field f1: 1 = raw.1:0x0;
	field f2: 1 = raw.1:0x0;
	field f3: 1 = raw.1:0x1;
	field f4: 1 = raw.1:0x0;
	field f5: 1 = raw.1:0x1;
	field f6: 1 = raw.1:0x0;
	field f7: 1 = raw.1:0x1;
	field f8: 1 = raw.1:0x1;
	field f9: 1 = raw.1:0x0;
    }
}
*/
