// @Harness: v2-init
// @Test: initialization of arrays of raws
// @Result: PASS

component array_raw02 {
    field a: 16[] = { 0xfe71, 0b100, 'c' };
    field av: 16  = a[0];
}

/* 
heap {
    record #0:4:array_raw02 {
	field a: raw.16[] = #1:raw.16[3];
	field av: raw.16 = raw.16:0xfe71;
    }
    record #1:3:16[3] {
	field 0: raw.16 = raw.16:0xfe71;
	field 1: raw.16 = raw.16:0x0004;
	field 2: raw.16 = raw.16:0x0063;
    }
} */
