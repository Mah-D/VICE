// @Harness: v2-init
// @Test: initialization of arrays of raws
// @Result: PASS

component array_raw01 {
    field a: 8[] = { 0xf, 0b00, 'c' };
    field av: 8  = a[0];
}

/* 
heap {
    record #0:4:array_raw01 {
	field a: raw.8[] = #1:raw.8[3];
	field av: raw.8 = raw.8:0x0f;
    }
    record #1:3:8[3] {
	field 0: raw.8 = raw.8:0x0f;
	field 1: raw.8 = raw.8:0x00;
	field 2: raw.8 = raw.8:0x63;
    }
} */
