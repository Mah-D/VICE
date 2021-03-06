// @Harness: v2-exec
// @Test: tir2c > raw types > concat operator
// @Result: 0=0, 1=1, 2=1, 3=1, 4=1, 5=1, 6=1, 7=1, 8=0

component raw_concat01 {
    field f1: 8  = 0x0  # 0xf;
    field f2: 16 = 0xf0 # 0x0f;
    field f3: 32 = 0xf3f3  # 0x1111;
    field f4: 14 = 0b1110101  # 0b1111111;
    field f5: 10 = 0b10101  # 0b10000;
    field f6: 6  = 0b101  # 0b100;
    field f7: 32 = 0xFFFF  # 0xF000;

    method main(arg: int): bool {
        if ( arg == 1 ) return (0x0  # 0xf) == f1;
        if ( arg == 2 ) return (0xf0 # 0x0f) == f2;
        if ( arg == 3 ) return (0xf3f3  # 0x1111) == f3;
        if ( arg == 4 ) return (0b1110101  # 0b1111111) == f4;
        if ( arg == 5 ) return (0b10101  # 0b10000) == f5;
        if ( arg == 6 ) return (0b101  # 0b100) == f6;
        if ( arg == 7 ) return (0xFFFF  # 0xF000) == f7;
	return false;
    }
}
