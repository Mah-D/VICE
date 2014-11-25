// @Harness: v2-init
// @Test: parsing precedence of arithmetic operators
// @Result: PASS

component prec03 {
    field r01: 4 = 0b0101 | 0b0010 & 0b0011; // & higher than |
    field r02: 4 = 0b0100 | 0b0011 & 0b0010; // & higher than |
    field r03: 4 = 0b1000 | 0b0010 ^ 0b1000; // ^ higher than |
    field r04: 4 = 0b1000 | 0b0001 ^ 0b1000; // ^ higher than |
    field r05: 4 = 0b1000 ^ 0b1000 & 0b0010; // & higher than ^
    field r06: 4 = 0b1000 ^ 0b1010 & 0b0011; // & higher than ^
}

/*
heap {
    record #0:10:prec03 {
        field r01: raw.4 = raw.4:0b0111;
        field r02: raw.4 = raw.4:0b0110;
	field r03: raw.4 = raw.4:0b1010;
	field r04: raw.4 = raw.4:0b1001;
	field r05: raw.4 = raw.4:0b1000;
	field r06: raw.4 = raw.4:0b1010;
    }
} */
