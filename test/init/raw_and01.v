// @Harness: v2-init
// @Test: initialization interpreter > raw types > and operator
// @Result: PASS

component eval_and01 {
    field f1: 4  = 0x0  & 0xf;
    field f2: 8  = 0xf0 & 0x0f;
    field f3: 16 = 0xf3f3  & 0x1111;
    field f4: 7  = 0b1110101  & 0b1111111;
    field f5: 5  = 0b10101  & 0b10000;
    field f6: 3  = 0b101  & 0b100;
    field f7: 32 = 0xFFFF0000  & 0xF000F000;
    field f8: 48 = 0xFF00FFFF0000  & 0xF000F000F000;
    field f9: 64 = 0xFFFF0000FFFF0000  & 0xF000F000F000F000;
}

/*
heap {
    record #0:1:eval_and01 {
	field f1: 4  = raw.4:0x0; 
	field f2: 8  = raw.8:0x00; 
	field f3: 16 = raw.16:0x1111; 
	field f4: 7  = raw.7:0x75; 
	field f5: 5  = raw.5:0x10; 
	field f6: 3  = raw.3:0x4; 
	field f7: 32 = raw.32:0xf0000000; 
	field f8: 48 = raw.48:0xf000f0000000; 
	field f9: 64 = raw.64:0xf0000000f0000000; 
    }
}
*/
