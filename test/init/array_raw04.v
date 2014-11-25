// @Harness: v2-init
// @Test: initialization of arrays of raws
// @Result: PASS

component array_raw04 {
    field a: 64[] = { 0xf0000000fe710000, 0b100, 'c', -1 };
    field av: 64  = a[0];
}

/* 
heap {
    record #0:4:array_raw04 {
	field a: raw.64[] = #1:raw.64[4];
	field av: raw.64 = raw.64:0xf0000000fe710000;
    }
    record #1:3:16[4] {
	field 0: raw.64 = raw.64:0xf0000000fe710000;
	field 1: raw.64 = raw.64:0x00000004;
	field 2: raw.64 = raw.64:0x00000063;
	field 3: raw.64 = raw.64:0xffffffffffffffff;
    }
} */
