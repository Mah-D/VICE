// @Harness: v2-init
// @Test: initialization of arrays of raws
// @Result: PASS

component array_raw03 {
    field a: 32[] = { 0xfe710000, 0b100, 'c', -1 };
    field av: 32  = a[0];
}

/* 
heap {
    record #0:4:array_raw03 {
	field a: raw.32[] = #1:raw.32[4];
	field av: raw.32 = raw.32:0xfe710000;
    }
    record #1:3:16[4] {
	field 0: raw.32 = raw.32:0xfe710000;
	field 1: raw.32 = raw.32:0x00000004;
	field 2: raw.32 = raw.32:0x00000063;
	field 3: raw.32 = raw.32:0xffffffff;
    }
} */
