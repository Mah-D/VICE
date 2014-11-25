// @Harness: v2-init
// @Test: initialization of arrays of raws
// @Result: PASS

component array_raw07 {
    field a: 29[] = { 0xfe71000, 0b100, 'c', 0xffedd };
    field av: 29  = a[0];
}

/* 
heap {
    record #0:2:array_raw07 {
	field a: raw.29[] = #1:raw.29[4];
	field av: raw.29 = raw.29:0xfe71000;
    }
    record #1:4:29[4] {
	field 0: raw.29 = raw.29:0xfe71000;
	field 1: raw.29 = raw.29:0x0000004;
	field 2: raw.29 = raw.29:0x0000063;
	field 3: raw.29 = raw.29:0x00ffedd;
    }
} */
