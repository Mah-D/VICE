// @Harness: v2-init
// @Test: initialization interpreter > raw types > or operator with expansion
// @Result: PASS

component eval_or02 {
    field f1: 8  = 0x10  | 0xf;
    field f2: 12  = 0x1f0 | 0x0f;
    field f3: 20 = 0x1f3f3  | 0x1111;
    field f4: 8  = 0b11110101  | 0b1111111;
    field f5: 6  = 0b110101  | 0b10000;
    field f6: 4  = 0b1101  | 0b100;
    field f7: 36 = 0x1FFFF0000  | 0xF000F000;
    field f8: 52 = 0x1FF00FFFF0000  | 0xF000F000F000;
    field f9: 64 = 0xFFFF0000FFFF0000  | 0xF000F000F000F000;
}

/*
heap {
    record #0:1:eval_or02 {
	field f1: 8  = raw.8:0x1f; 
	field f2: 12 = raw.12:0x1ff; 
	field f3: 20 = raw.20:0x1f3f3; 
	field f4: 8  = raw.8:0xff;
	field f5: 6  = raw.6:0x35; 
	field f6: 4  = raw.4:0xd; 
	field f7: 36 = raw.36:0x1fffff000; 
	field f8: 52 = raw.52:0x1ff00fffff000; 
	field f9: 64 = raw.64:0xfffff000fffff000; 
    }
}
*/
