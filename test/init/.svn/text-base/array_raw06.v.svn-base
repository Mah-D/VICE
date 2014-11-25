// @Harness: v2-init
// @Test: initialization of arrays of raws
// @Result: PASS

component array_raw06 {
    field a: 13[] = { 0xfe7, 0b100, 'c' };
    field av: 13  = a[0];
}

/* 
heap {
    record #0:2:array_raw06 {
	field a: raw.13[] = #1:raw.13[3];
	field av: raw.13 = raw.13:0xfe7;
    }
    record #1:3:13[3] {
	field 0: raw.13 = raw.13:0xfe7;
	field 1: raw.13 = raw.13:0x004;
	field 2: raw.13 = raw.13:0x063;
    }
} */
