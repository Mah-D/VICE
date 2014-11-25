// @Harness: v2-init
// @Test: parsing precedence of bitwise operators vs bit index
// @Result: PASS

component prec04 {
    field r01: 4 = 0b0100 | 0b0010[0];
    field r02: 4 = 0b0100 | 0b0010[1];
    field r03: 4 = 0b1000 ^ 0b0010[0];
    field r04: 4 = 0b1000 ^ 0b0010[1];
    field r05: 4 = 0b1001 & 0b1000[0];
    field r06: 4 = 0b1001 & 0b1010[1];
}

/*
heap {
    record #0:10:prec04 {
        field r01: raw.4 = raw.4:0b0100;
        field r02: raw.4 = raw.4:0b0101;
	field r03: raw.4 = raw.4:0b1000;
	field r04: raw.4 = raw.4:0b1001;
	field r05: raw.4 = raw.4:0b0000;
	field r06: raw.4 = raw.4:0b0001;
    }
} */
