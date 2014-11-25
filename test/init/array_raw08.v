// @Harness: v2-init
// @Test: initialization of arrays of raws
// @Result: PASS

component array_raw08 {
    field a: 51[] = { 0x1000fe710000, 0b100, 'c', -1 };
    field av: 51  = a[0];
}

/* 
heap {
    record #0:2:array_raw08 {
	field a: raw.51[] = #1:raw.51[4];
	field av: raw.51 = raw.51:0x1000fe710000;
    }
    record #1:4:51[4] {
	field 0: raw.51 = raw.51:0x1000fe710000;
	field 1: raw.51 = raw.51:0x00000004;
	field 2: raw.51 = raw.51:0x00000063;
	field 3: raw.51 = raw.51:0x7ffffffffffff;
    }
} */
