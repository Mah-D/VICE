// @Harness: v2-init
// @Test: initialization of arrays of raws
// @Result: PASS

component array_raw05 {
    field a: 5[] = { 0xf, 0b10, 0b11100 };
    field av: 5  = a[0];
}

/* 
heap {
    record #0:4:array_raw05 {
	field a: raw.5[] = #1:raw.5[3];
	field av: raw.5 = raw.5:0xf;
    }
    record #1:3:5[3] {
	field 0: raw.5 = raw.5:0x0f;
	field 1: raw.5 = raw.5:0x02;
	field 2: raw.5 = raw.5:0x1c;
    }
} */
