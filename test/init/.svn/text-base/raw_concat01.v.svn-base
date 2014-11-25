// @Harness: v2-init
// @Test: initialization interpreter > raw types > concat operator
// @Result: PASS

component raw_concat01 {
    field f1: 8  = 0x0  # 0xf;
    field f2: 16 = 0xf0 # 0x0f;
    field f3: 32 = 0xf3f3  # 0x1111;
    field f4: 14 = 0b1110101  # 0b1111111;
    field f5: 10 = 0b10101  # 0b10000;
    field f6: 6  = 0b101  # 0b100;
    field f7: 32 = 0xFFFF  # 0xF000;
    field f8: 48 = 0xF0  # 0x00F000F000;
    field f9: 64 = 0xFFFF  # 0xF000F000F000;
}

/*
heap {
    record #0:1:raw_concat01 {
	field f1: 8  = raw.8:0x0f; 
	field f2: 16 = raw.16:0xf00f; 
	field f3: 32 = raw.32:0xf3f31111; 
	field f4: 14 = raw.14:0x3aff; 
	field f5: 10 = raw.10:0x2b0; 
	field f6: 6  = raw.6:0x2c; 
	field f7: 32 = raw.32:0xfffff000; 
	field f8: 48 = raw.48:0xf000f000f000; 
	field f9: 64 = raw.64:0xfffff000f000f000; 
    }
}
*/
