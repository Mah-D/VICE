/*
 * This module provides the CONTROL functionality for the Chipcon1000 series radio.
 * It exports both a standard control 32erface and a custom interface to control
 * CC1000 operation.
 * @author Xiaoli Gong
 */

component CC1000Control {
  
	field CC1K_433_002_MHZ: 8	= 0x00;
	field CC1K_915_998_MHZ: 8	= 0x01;
	field CC1K_434_845_MHZ: 8	= 0x02;
	field CC1K_914_077_MHZ: 8	= 0x03;
	field CC1K_315_178_MHZ: 8	= 0x04;

	field CC1K_DEF_PRESET: 8	= CC1K_433_002_MHZ;

	field CC1K_MAIN: 8	= 0x00;
	field CC1K_FREQ_2A: 8	= 0x01;
	field CC1K_FREQ_1A: 8	= 0x02;
	field CC1K_FREQ_0A: 8	= 0x03;
	field CC1K_FREQ_2B: 8	= 0x04;
	field CC1K_FREQ_1B: 8	= 0x05;
	field CC1K_FREQ_0B: 8	= 0x06;
	field CC1K_FSEP1: 8	= 0x07;
	field CC1K_FSEP0: 8	= 0x08;
	field CC1K_CURRENT: 8	= 0x09;
	field CC1K_FRONT_END :8	= 0x0A; // 10
	field CC1K_PA_POW: 8	= 0x0B; // 11
	field CC1K_PLL: 8	= 0x0C; // 12
	field CC1K_LOCK: 8	= 0x0D; // 13
	field CC1K_CAL: 8	= 0x0E; // 14
	field CC1K_MODEM2: 8	= 0x0F; // 15
	field CC1K_MODEM1: 8	= 0x10; // 16
	field CC1K_MODEM0: 8	= 0x11; // 17
	field CC1K_MATCH: 8	= 0x12; // 18
	field CC1K_FSCTRL: 8	= 0x13; // 19
	field CC1K_FSHAPE7: 8	= 0x14; // 20
	field CC1K_FSHAPE6: 8	= 0x15; // 21
	field CC1K_FSHAPE5: 8	= 0x16; // 22
	field CC1K_FSHAPE4: 8	= 0x17; // 23
	field CC1K_FSHAPE3: 8	= 0x18; // 24
	field CC1K_FSHAPE2: 8	= 0x19; // 25
	field CC1K_FSHAPE1: 8	= 0x1A; // 26
	field CC1K_FSDELAY: 8	= 0x1B; // 27
	field CC1K_PRESCALER: 8	= 0x1C; // 28
	field CC1K_TEST6: 8	= 0x40; // 64
	field CC1K_TEST5: 8	= 0x41; // 66
	field CC1K_TEST4: 8	= 0x42; // 67
	field CC1K_TEST3: 8	= 0x43; // 68
	field CC1K_TEST2: 8	= 0x44; // 69
	field CC1K_TEST1: 8	= 0x45; // 70
	field CC1K_TEST0: 8	= 0x46; // 71

	// MAIN Register Bit Posititions
	field CC1K_RXTX: int	= 7;
	field CC1K_F_REG: int	= 6;
	field CC1K_RX_PD: int	= 5;
	field CC1K_TX_PD: int	= 4;
	field CC1K_FS_PD: int	= 3;
	field CC1K_CORE_PD: int	= 2;
	field CC1K_BIAS_PD: int	= 1;
	field CC1K_RESET_N: int	= 0;

	// CURRENT Register Bit Positions
	field CC1K_VCO_CURRENT: int	= 4;
	field CC1K_LO_DRIVE: int	= 2;

	field CC1K_PA_DRIVE: int	= 0;

	// FRONT_END Register Bit Positions
	field CC1K_BUF_CURRENT: int	= 5;
	field CC1K_LNA_CURRENT: int	= 3;
	field CC1K_IF_RSSI: int		= 1;
	field CC1K_XOSC_BYPASS: int	= 0;

	// PA_POW Register Bit Positions
	field CC1K_PA_HIGHPOWER: int	= 4;
	field CC1K_PA_LOWPOWER: int	= 0;

	// PLL Register Bit Positions
	field CC1K_EXT_FILTER: int	= 7;
	field CC1K_REFDIV: int		= 3;
	field CC1K_ALARM_DISABLE: int	= 2;
	field CC1K_ALARM_H: int		= 1;
	field CC1K_ALARM_L: int		= 0;

	// LOCK Register Bit Positions
	field CC1K_LOCK_SELECT: int		= 4;
	field CC1K_PLL_LOCK_ACCURACY: int	= 3;
	field CC1K_PLL_LOCK_LENGTH: int		= 2;
	field CC1K_LOCK_INSTANT: int		= 1;
	field CC1K_LOCK_CONTINUOUS: int		= 0;

	// CAL Register Bit Positions
	field CC1K_CAL_START: int	= 7;
	field CC1K_CAL_DUAL: int	= 6;
	field CC1K_CAL_WAIT: int	= 5;
	field CC1K_CAL_CURRENT: int	= 4;
	field CC1K_CAL_COMPLETE: int	= 3;
	field CC1K_CAL_ITERATE: int	= 0;

	// MODEM2 Register Bit Positions
	field CC1K_PEAKDETECT: int		= 7;
	field CC1K_PEAK_LEVEL_OFFSET: int	= 0;

	// MODEM1 Register Bit Positions
	field CC1K_MLIMIT: int		= 5;
	field CC1K_LOCK_AVG_IN: int	= 4;
	field CC1K_LOCK_AVG_MODE: int	= 3;
	field CC1K_SETTLING: int	= 1;
	field CC1K_MODEM_RESET_N: int	= 0;

	// MODEM0 Register Bit Positions
	field CC1K_BAUDRATE: int	= 4;
	field CC1K_DATA_FORMAT: int	= 2;
	field CC1K_XOSC_FREQ: int	= 0;

	// MATCH Register Bit Positions
	field CC1K_RX_MATCH: int	= 4;
	field CC1K_TX_MATCH: int	= 0;

	// FSCTLR Register Bit Positions
	field CC1K_DITHER1: int		= 3;
	field CC1K_DITHER0: int		= 2;
	field CC1K_SHAPE: int		= 1;
	field CC1K_FS_RESET_N: int	= 0;

	// PRESCALER Register Bit Positions
	field CC1K_PRE_SWING: int	= 6;
	field CC1K_PRE_CURRENT: int	= 4;
	field CC1K_IF_INPUT: int	= 3;
	field CC1K_IF_FRONT: int	= 2;

	// TEST6 Register Bit Positions
	field CC1K_LOOPFILTER_TP1: int	= 7;
	field CC1K_LOOPFILTER_TP2: int	= 6;
	field CC1K_CHP_OVERRIDE: int	= 5;
	field CC1K_CHP_CO: int		= 0;

	// TEST5 Register Bit Positions
	field CC1K_CHP_DISABLE: int	= 5;
	field CC1K_VCO_OVERRIDE: int	= 4;
	field CC1K_VCO_AO: int		= 0;

	// TEST3 Register Bit Positions
	field CC1K_BREAK_LOOP: int	= 4;
	field CC1K_CAL_DAC_OPEN: int	= 0;

	field gCurrentChannel:32;
	field gCurrentParameters: 8[] = new 8[31]; // access to this array must be in no race condition
	field IF: int = 150000;
	field FREQ_MIN: int = 4194304;
	field FREQ_MAX: int = 16751615;

	field FRefTbl: int[];
	field CorTbl: int[];  
	field FSepTbl: int[];

	field CC1K_Params:8[][];

	method TOSH_uwait(time: int) {
		local i: int;
		local j: int;
		local dummy: int = 0;
		for (j = 0; j < time; j++){
			dummy++;
			dummy++;
			dummy++;
		}
	}

	constructor() {
		local i: int;
		local j: int;
		FRefTbl = new int [9];
		FRefTbl[0] = 2457600;
		FRefTbl[1] = 2106514;
		FRefTbl[2] = 1843200;
		FRefTbl[3] = 1638400;
		FRefTbl[4] = 1474560;
		FRefTbl[5] = 1340509;
		FRefTbl[6] = 1228800;
		FRefTbl[7] = 1134277;
		FRefTbl[8] = 1053257;

		CorTbl = new int[9];
		CorTbl[0] = 1213;
		CorTbl[1] = 1416;
		CorTbl[2] = 1618;
		CorTbl[3] = 1820;
		CorTbl[4] = 2022;
		CorTbl[5] = 2224;
		CorTbl[6] = 2427;
		CorTbl[7] = 2629;
		CorTbl[8] = 2831;

		FSepTbl = new int[9];
		FSepTbl[0] = 0x01AA::int;
		FSepTbl[1] = 0x01F1::int;
		FSepTbl[2] = 0x0238::int;
		FSepTbl[3] = 0x0280::int;
		FSepTbl[4] = 0x02C7::int;
		FSepTbl[5] = 0x030E::int;
		FSepTbl[6] = 0x0355::int;
		FSepTbl[7] = 0x039C::int;
		FSepTbl[8] = 0x03E3::int;

		CC1K_Params = new 8[][6];
		for (i = 0; i < 6; i++) {
			CC1K_Params[i] = new 8[31];
			for(j = 0; j <31; j++) CC1K_Params[i][j] = 0::8;
		}


		// (0) 433.002 MHz channel, 19.2 Kbps data, Manchester Encoding, High Side LO
		// MAIN   0x00
		CC1K_Params[0][0] = 0x31;
		// FREQ2A,FREQ1A,FREQ0A  0x01-0x03
		CC1K_Params[0][1] = 0x58;
		CC1K_Params[0][2] = 0x00;
		CC1K_Params[0][3] = 0x00;					
		// FREQ2B,FREQ1B,FREQ0B  0x04-0x06
		CC1K_Params[0][4] = 0x57;
		CC1K_Params[0][5] = 0xf6;
		CC1K_Params[0][6] = 0x85;    
		// FSEP1, FSEP0     0x07-0x08
		CC1K_Params[0][7] = 0x03;
		CC1K_Params[0][8] = 0x55;
		// CURRENT (RX MODE VALUE)   0x09 (also see below)
		CC1K_Params[0][9] = ((0x04<<CC1K_VCO_CURRENT) | (0x01<<CC1K_LO_DRIVE))::8;	
		// FRONT_END  0x0a
		CC1K_Params[0][10] = (0x01<<CC1K_IF_RSSI)::8;
		// PA_POW  0x0b
		CC1K_Params[0][11] = ((0x00<<CC1K_PA_HIGHPOWER) | (0x0f<<CC1K_PA_LOWPOWER))::8;
		// PLL  0x0c
		CC1K_Params[0][12] = ((12<<CC1K_REFDIV))::8;		
		// LOCK  0x0d
		CC1K_Params[0][13] = ((0x0e<<CC1K_LOCK_SELECT))::8;
		// CAL  0x0e
		CC1K_Params[0][14] = ((1<<CC1K_CAL_WAIT) | (6<<CC1K_CAL_ITERATE))::8;	
		// MODEM2  0x0f
		CC1K_Params[0][15] = ((0<<CC1K_PEAKDETECT) | (28<<CC1K_PEAK_LEVEL_OFFSET))::8;
		// MODEM1  0x10
		CC1K_Params[0][16] = ((3<<CC1K_MLIMIT) | (1<<CC1K_LOCK_AVG_MODE) | (1 /*CC1K_Settling*/<<CC1K_SETTLING) | (1<<CC1K_MODEM_RESET_N))::8;
		// MODEM0  0x11
		CC1K_Params[0][17] = ((5<<CC1K_BAUDRATE) | (1<<CC1K_DATA_FORMAT) | (1<<CC1K_XOSC_FREQ))::8;
		// MATCH  0x12
		CC1K_Params[0][18] = ((0x07<<CC1K_RX_MATCH) | (0x00<<CC1K_TX_MATCH))::8;
		// FSCTRL 0x13
		CC1K_Params[0][19] = ((1<<CC1K_FS_RESET_N))::8;
		// FSHAPE7 - FSHAPE1   0x14-0x1a
		CC1K_Params[0][20] = 0x00;
		CC1K_Params[0][21] = 0x00;
		CC1K_Params[0][22] = 0x00;
		CC1K_Params[0][23] = 0x00;
		CC1K_Params[0][24] = 0x00;
		CC1K_Params[0][25] = 0x00;
		CC1K_Params[0][26] = 0x00;	
		// FSDELAY   0x1b
		CC1K_Params[0][27] = 0x00;
		// PRESCALER    0x1c
		CC1K_Params[0][28] = 0x00;
		// CURRENT (TX MODE VALUE)  0x1d
		CC1K_Params[0][29] = ((8<<CC1K_VCO_CURRENT) | (1<<CC1K_PA_DRIVE))::8;
		// High side LO  0x1e (i.e. do we need to invert the data?)
		
		CC1K_Params[0][30] = 0x01;

		// (1) 914.9988 MHz channel, 19.2 Kbps data, Manchester Encoding, High Side LO
		CC1K_Params[1][0] = 0x31;
		// FREQ2A,FREQ1A,FREQ0A  0x01-0x03
		CC1K_Params[1][1] = 0x7c;
		CC1K_Params[1][2] = 0x00;
		CC1K_Params[1][3] = 0x00;					
		// FREQ2B,FREQ1B,FREQ0B  0x04-0x06
		CC1K_Params[1][4] = 0x7b;
		CC1K_Params[1][5] = 0xf9;
		CC1K_Params[1][6] = 0xae;					
		// FSEP1, FSEP0     0x07-0x8
		CC1K_Params[1][7] = 0x02;
		CC1K_Params[1][8] = 0x38;
		// CURRENT (RX MODE VALUE)   0x09 (also see below)
		CC1K_Params[1][9] = ((8<<CC1K_VCO_CURRENT) | (3<<CC1K_LO_DRIVE))::8;
		//0x8C,	
		// FRONT_END  0x0a
		CC1K_Params[1][10] = ((1<<CC1K_BUF_CURRENT) | (2<<CC1K_LNA_CURRENT) | (1<<CC1K_IF_RSSI))::8;
		//0x32,
		// PA_POW  0x0b
		CC1K_Params[1][11] = ((0x08<<CC1K_PA_HIGHPOWER) | (0x00<<CC1K_PA_LOWPOWER))::8;
		//0xff,
		// PLL  0xc
		CC1K_Params[1][12] = ((8<<CC1K_REFDIV))::8;		
		//0x40,
		// LOCK  0xd
		CC1K_Params[1][13] = ((0x01<<CC1K_LOCK_SELECT))::8;
		//0x10,
		// CAL  0xe
		CC1K_Params[1][14] = ((1<<CC1K_CAL_WAIT) | (6<<CC1K_CAL_ITERATE))::8;
		//0x26,
		// MODEM2  0xf
		CC1K_Params[1][15] = ((1<<CC1K_PEAKDETECT) | (33<<CC1K_PEAK_LEVEL_OFFSET))::8;
		//0xA1,
		// MODEM1  0x10
		CC1K_Params[1][16] = ((3<<CC1K_MLIMIT) | (1<<CC1K_LOCK_AVG_MODE) | (1 /*CC1K_Settling*/<<CC1K_SETTLING) | (1<<CC1K_MODEM_RESET_N))::8;
		//0x6f, 
		// MODEM0  0x11
		CC1K_Params[1][17] = ((5<<CC1K_BAUDRATE) | (1<<CC1K_DATA_FORMAT) | (1<<CC1K_XOSC_FREQ))::8;
		//0x55,
		// MATCH 0x12
		CC1K_Params[1][18] = ((0x01<<CC1K_RX_MATCH) | (0x00<<CC1K_TX_MATCH))::8;
		//0x10,
		// FSCTRL  0x13
		CC1K_Params[1][19] = ((1<<CC1K_FS_RESET_N))::8;
		//0x01,
		// FSHAPE7 - FSHAPE1   0x14..0x1a
		CC1K_Params[1][20] = 0x00;
		CC1K_Params[1][21] = 0x00;
		CC1K_Params[1][22] = 0x00;
		CC1K_Params[1][23] = 0x00;
		CC1K_Params[1][24] = 0x00;
		CC1K_Params[1][25] = 0x00;
		CC1K_Params[1][26] = 0x00;	
		// FSDELAY   0x1b
		CC1K_Params[1][27] = 0x00;	
		// PRESCALER    0x1c
		CC1K_Params[1][28] = 0x00;
		// CURRENT (TX MODE VALUE)  0x1d
		CC1K_Params[1][29] = ((15<<CC1K_VCO_CURRENT) | (3<<CC1K_PA_DRIVE))::8;
		//0xf3,
		// High side LO  0x1e (i.e. do we need to invert the data?)
		CC1K_Params[1][30] = 0x01;


		// (2) 434.845200 MHz channel, 19.2 Kbps data, Manchester Encoding, High Side LO
		CC1K_Params[2][0] = 0x31;
		// FREQ2A,FREQ1A,FREQ0A  0x01-0x03
		CC1K_Params[2][1] = 0x51;
		CC1K_Params[2][2] = 0x00;
		CC1K_Params[2][3] = 0x00;					
		// FREQ2B,FREQ1B,FREQ0B  0x04-0x06
		CC1K_Params[2][4] = 0x50;
		CC1K_Params[2][5] = 0xf7;
		CC1K_Params[2][6] = 0x4F;		//XBOW
		// FSEP1, FSEP0     0x07-0x08
		CC1K_Params[2][7] = 0x03;
		CC1K_Params[2][8] = 0x0E;
		// CURRENT (RX MODE VALUE)   0x09 (also see below)
		CC1K_Params[2][9] = ((4<<CC1K_VCO_CURRENT) | (1<<CC1K_LO_DRIVE))::8;
		// FRONT_END  0x0a
		CC1K_Params[2][10] = ((1<<CC1K_IF_RSSI))::8;
		// PA_POW  0x0b
		CC1K_Params[2][11] = ((0x00<<CC1K_PA_HIGHPOWER) | (0x0f<<CC1K_PA_LOWPOWER))::8;
		// PLL  0x0c
		CC1K_Params[2][12] = ((11<<CC1K_REFDIV))::8;		
		// LOCK  0x0d
		CC1K_Params[2][13] = ((0xe<<CC1K_LOCK_SELECT))::8;
		// CAL  0x0e
		CC1K_Params[2][14] = ((1<<CC1K_CAL_WAIT) | (6<<CC1K_CAL_ITERATE))::8;
		// MODEM2  0x0f
		CC1K_Params[2][15] = ((1<<CC1K_PEAKDETECT) | (33<<CC1K_PEAK_LEVEL_OFFSET))::8;
		// MODEM1  0x10
		CC1K_Params[2][16] = ((3<<CC1K_MLIMIT) | (1<<CC1K_LOCK_AVG_MODE) | (1 /*CC1K_Settling*/<<CC1K_SETTLING) | (1<<CC1K_MODEM_RESET_N))::8;
		// MODEM0  0x11
		CC1K_Params[2][17] = ((5<<CC1K_BAUDRATE) | (1<<CC1K_DATA_FORMAT) | (1<<CC1K_XOSC_FREQ))::8;
		// MATCH  0x12
		CC1K_Params[2][18] = ((0x07<<CC1K_RX_MATCH) | (0x00<<CC1K_TX_MATCH))::8;
		// FSCTRL 0x13
		CC1K_Params[2][19] = ((1<<CC1K_FS_RESET_N))::8;
		// FSHAPE7 - FSHAPE1   0x14-0x1a
		CC1K_Params[2][20] = 0x00;
		CC1K_Params[2][21] = 0x00;
		CC1K_Params[2][22] = 0x00;
		CC1K_Params[2][23] = 0x00;
		CC1K_Params[2][24] = 0x00;
		CC1K_Params[2][25] = 0x00;
		CC1K_Params[2][26] = 0x00;	
		// FSDELAY   0x1b
		CC1K_Params[2][27] = 0x00;	
		// PRESCALER    0x1c
		CC1K_Params[2][28] = 0x00;
		// CURRENT (TX MODE VALUE)  0x1d
		CC1K_Params[2][29] = ((8<<CC1K_VCO_CURRENT) | (1<<CC1K_PA_DRIVE))::8;
		// High side LO  0x1e (i.e. do we need to invert the data?)
		CC1K_Params[2][30] = 0x01;  

		// (3) 914.077 MHz channel, 19.2 Kbps data, Manchester Encoding, High Side LO
		CC1K_Params[3][0] = 0x31;
		// FREQ2A,FREQ1A,FREQ0A  0x01-0x03
		CC1K_Params[3][1] = 0x5c;
		CC1K_Params[3][2] = 0xe0;
		CC1K_Params[3][3] = 0x00;					
		// FREQ2B,FREQ1B,FREQ0B  0x04-0x06
		CC1K_Params[3][4] = 0x5c;
		CC1K_Params[3][5] = 0xdb;
		CC1K_Params[3][6] = 0x42;					
		// FSEP1, FSEP0     0x07-0x8
		CC1K_Params[3][7] = 0x01;
		CC1K_Params[3][8] = 0xAA;
		// CURRENT (RX MODE VALUE)   0x09 (also see below)
		CC1K_Params[3][9] = ((8<<CC1K_VCO_CURRENT) | (3<<CC1K_LO_DRIVE))::8;
		//0x8C,	
		// FRONT_END  0x0a
		CC1K_Params[3][10] = ((1<<CC1K_BUF_CURRENT) | (2<<CC1K_LNA_CURRENT) | (1<<CC1K_IF_RSSI))::8;
		//0x32,
		// PA_POW  0x0b
		CC1K_Params[3][11] = ((0x08<<CC1K_PA_HIGHPOWER) | (0x00<<CC1K_PA_LOWPOWER))::8;
		//0xff,
		// PLL  0xc
		CC1K_Params[3][12] = ((6<<CC1K_REFDIV))::8;
		//0x40,
		// LOCK  0xd
		CC1K_Params[3][13] = ((0x01<<CC1K_LOCK_SELECT))::8;
		//0x10,
		// CAL  0xe
		CC1K_Params[3][14] = ((1<<CC1K_CAL_WAIT) | (6<<CC1K_CAL_ITERATE))::8;
		//0x26,
		// MODEM2  0xf
		CC1K_Params[3][15] = ((1<<CC1K_PEAKDETECT) | (33<<CC1K_PEAK_LEVEL_OFFSET))::8;
		//0xA1,
		// MODEM1  0x10
		CC1K_Params[3][16] = ((3<<CC1K_MLIMIT) | (1<<CC1K_LOCK_AVG_MODE) | (1 /*CC1K_Settling*/<<CC1K_SETTLING) | (1<<CC1K_MODEM_RESET_N))::8;
		//0x6f, 
		// MODEM0  0x11
		CC1K_Params[3][17] = ((5<<CC1K_BAUDRATE) | (1<<CC1K_DATA_FORMAT) | (1<<CC1K_XOSC_FREQ))::8;
		//0x55,
		// MATCH 0x12
		CC1K_Params[3][18] = ((0x01<<CC1K_RX_MATCH) | (0x00<<CC1K_TX_MATCH))::8;
		//0x10,
		// FSCTRL  0x13
		CC1K_Params[3][19] = ((1<<CC1K_FS_RESET_N))::8;
		//0x01,
		// FSHAPE7 - FSHAPE1   0x14..0x1a
		CC1K_Params[3][20] = 0x00;
		CC1K_Params[3][21] = 0x00;
		CC1K_Params[3][22] = 0x00;
		CC1K_Params[3][23] = 0x00;
		CC1K_Params[3][24] = 0x00;
		CC1K_Params[3][25] = 0x00;
		CC1K_Params[3][26] = 0x00;	
		// FSDELAY   0x1b
		CC1K_Params[3][27] = 0x00;	
		// PRESCALER    0x1c
		CC1K_Params[3][28] = 0x00;
		// CURRENT (TX MODE VALUE)  0x1d
		CC1K_Params[3][29] = ((15<<CC1K_VCO_CURRENT) | (3<<CC1K_PA_DRIVE))::8;
		//0xf3,
		// High side LO  0x1e (i.e. do we need to invert the data?)
		CC1K_Params[3][30] = 0x01;     

		// (4) 315.178985 MHz channel, 38.4 Kbps data, Manchester Encoding, High Side LO
		CC1K_Params[4][0] = 0x31;
		// FREQ2A,FREQ1A,FREQ0A  0x01-0x03
		CC1K_Params[4][1] = 0x45;
		CC1K_Params[4][2] = 0x60;
		CC1K_Params[4][3] = 0x00;					
		// FREQ2B,FREQ1B,FREQ0B  0x04-0x06
		CC1K_Params[4][4] = 0x45;
		CC1K_Params[4][5] = 0x55;
		CC1K_Params[4][6] = 0xBB;
		// FSEP1, FSEP0     0x07-0x08
		CC1K_Params[4][7] = 0x03;
		CC1K_Params[4][8] = 0x9C;
		// CURRENT (RX MODE VALUE)   0x09 (also see below)
		CC1K_Params[4][9] = ((8<<CC1K_VCO_CURRENT) | (0<<CC1K_LO_DRIVE))::8;
		// FRONT_END  0x0a
		CC1K_Params[4][10] = ((1<<CC1K_IF_RSSI))::8;
		// PA_POW  0x0b
		CC1K_Params[4][11] = ((0x00<<CC1K_PA_HIGHPOWER) | (0x0f<<CC1K_PA_LOWPOWER))::8; 
		// PLL  0x0c
		CC1K_Params[4][12] = ((13<<CC1K_REFDIV))::8;		
		// LOCK  0x0d
		CC1K_Params[4][13] = ((0x0e<<CC1K_LOCK_SELECT))::8;
		// CAL  0x0e
		CC1K_Params[4][14] = ((1<<CC1K_CAL_WAIT) | (6<<CC1K_CAL_ITERATE))::8;	
		// MODEM2  0x0f
		CC1K_Params[4][15] = ((1<<CC1K_PEAKDETECT) | (33<<CC1K_PEAK_LEVEL_OFFSET))::8;
		// MODEM1  0x10
		CC1K_Params[4][16] = ((3<<CC1K_MLIMIT) | (1<<CC1K_LOCK_AVG_MODE) | (1 /*CC1K_Settling*/<<CC1K_SETTLING) | (1<<CC1K_MODEM_RESET_N))::8; 
		// MODEM0  0x11
		CC1K_Params[4][17] = ((5<<CC1K_BAUDRATE) | (1<<CC1K_DATA_FORMAT) | (0<<CC1K_XOSC_FREQ))::8;
		// MATCH  0x12
		CC1K_Params[4][18] = ((0x07<<CC1K_RX_MATCH) | (0x00<<CC1K_TX_MATCH))::8;
		// FSCTRL 0x13
		CC1K_Params[4][19] = ((1<<CC1K_FS_RESET_N))::8;			
		// FSHAPE7 - FSHAPE1   0x14-0x1a
		CC1K_Params[4][20] = 0x00;
		CC1K_Params[4][21] = 0x00;
		CC1K_Params[4][22] = 0x00;
		CC1K_Params[4][23] = 0x00;
		CC1K_Params[4][24] = 0x00;
		CC1K_Params[4][25] = 0x00;
		CC1K_Params[4][26] = 0x00;	
		// FSDELAY   0x1b
		CC1K_Params[4][27] = 0x00;
		// PRESCALER    0x1c
		CC1K_Params[4][28] = 0x00;
		// CURRENT (TX MODE VALUE)  0x1d
		CC1K_Params[4][29] = ((8<<CC1K_VCO_CURRENT) | (1<<CC1K_PA_DRIVE))::8;
		// High side LO  0x1e (i.e. do we need to invert the data?)
		CC1K_Params[4][30] = 0x01;

		// (5) Spare
 		CC1K_Params[5][0] = 0x31;
		// FREQ2A,FREQ1A,FREQ0A  0x01-0x03
 		CC1K_Params[5][1] = 0x58;
 		CC1K_Params[5][2] = 0x00;
 		CC1K_Params[5][3] = 0x00;					
		// FREQ2B,FREQ1B,FREQ0B  0x04-0x06
 		CC1K_Params[5][4] = 0x57;
 		CC1K_Params[5][5] = 0xf6;
 		CC1K_Params[5][6] = 0x85;		//XBOW
		// FSEP1, FSEP0     0x07-0x08
 		CC1K_Params[5][7] = 0x03;
 		CC1K_Params[5][8] = 0x55;
		// CURRENT (RX MODE VALUE)   0x09 (also see below)
 		CC1K_Params[5][9] = ((8<<CC1K_VCO_CURRENT) | (4<<CC1K_LO_DRIVE))::8;	
		// FRONT_END  0x0a
 		CC1K_Params[5][10] = ((1<<CC1K_IF_RSSI))::8;
		// PA_POW  0x0b
 		CC1K_Params[5][11] = ((0x00<<CC1K_PA_HIGHPOWER) | (0x0f<<CC1K_PA_LOWPOWER))::8; 
		// PLL  0x0c
 		CC1K_Params[5][12] = ((12<<CC1K_REFDIV))::8;		
		// LOCK  0x0d
 		CC1K_Params[5][13] = ((0x0e<<CC1K_LOCK_SELECT))::8;
		// CAL  0x0e
 		CC1K_Params[5][14] = ((1<<CC1K_CAL_WAIT) | (6<<CC1K_CAL_ITERATE))::8;	
		// MODEM2  0x0f
 		CC1K_Params[5][15] = ((1<<CC1K_PEAKDETECT) | (33<<CC1K_PEAK_LEVEL_OFFSET))::8;
		// MODEM1  0x10
 		CC1K_Params[5][16] = ((3<<CC1K_MLIMIT) | (1<<CC1K_LOCK_AVG_MODE) | (1 /*CC1K_Settling*/<<CC1K_SETTLING) | (1<<CC1K_MODEM_RESET_N))::8;
		// MODEM0  0x11
 		CC1K_Params[5][17] = ((5<<CC1K_BAUDRATE) | (1<<CC1K_DATA_FORMAT) | (1<<CC1K_XOSC_FREQ))::8;
		// MATCH  0x12
 		CC1K_Params[5][18] = ((0x07<<CC1K_RX_MATCH) | (0x00<<CC1K_TX_MATCH))::8;
		// FSCTRL 0x13
 		CC1K_Params[5][19] = ((1<<CC1K_FS_RESET_N))::8;			
		// FSHAPE7 - FSHAPE1   0x14-0x1a
 		CC1K_Params[5][20] = 0x00;
 		CC1K_Params[5][21] = 0x00;
 		CC1K_Params[5][22] = 0x00;
 		CC1K_Params[5][23] = 0x00;
 		CC1K_Params[5][24] = 0x00;
 		CC1K_Params[5][25] = 0x00;
 		CC1K_Params[5][26] = 0x00;	
		// FSDELAY   0x1b
 		CC1K_Params[5][27] = 0x00;	
		// PRESCALER    0x1c
 		CC1K_Params[5][28] = 0x00;
		// CURRENT (TX MODE VALUE)  0x1d
 		CC1K_Params[5][29] = ((8<<CC1K_VCO_CURRENT) | (1<<CC1K_PA_DRIVE))::8;
		// High side LO  0x1e (i.e. do we need to invert the data?)
 		CC1K_Params[5][30] = 0x01;
	}

	// place the radio chip in calibrate mode.
	method chipcon_cal(): boolean {
		local temp:8 = 0x00;

		HPLChipcon.write(CC1K_PA_POW, 0x00);	// turn off rf amp
		HPLChipcon.write(CC1K_TEST4, 0x3f);	 // chip rate >= 38.4kb

		// RX - configure main freq A
		HPLChipcon.write(CC1K_MAIN, ((1<<CC1K_TX_PD) | (1<<CC1K_RESET_N))::8);

		// start cal
		HPLChipcon.write(CC1K_CAL,
			((1<<CC1K_CAL_START) | 
			 (1<<CC1K_CAL_WAIT) | (6<<CC1K_CAL_ITERATE))::8);
		do {
			temp = HPLChipcon.read(CC1K_CAL);
		} while(temp[CC1K_CAL_COMPLETE] == 0b0);

		// exit cal mode
		HPLChipcon.write(CC1K_CAL, ((1<<CC1K_CAL_WAIT) | (6<<CC1K_CAL_ITERATE))::8);

		// TX - configure main freq B
		HPLChipcon.write(CC1K_MAIN,
			((1<<CC1K_RXTX) | (1<<CC1K_F_REG) | (1<<CC1K_RX_PD) | 
			 (1<<CC1K_RESET_N))::8);
		// Set TX current
		HPLChipcon.write(CC1K_CURRENT, gCurrentParameters[29]);
		HPLChipcon.write(CC1K_PA_POW, 0x00);
		// TOSH_uwait(2000);

		// start cal
		HPLChipcon.write(CC1K_CAL,
			((1<<CC1K_CAL_START) | 
			 (1<<CC1K_CAL_WAIT) | (6<<CC1K_CAL_ITERATE))::8);
		do {
			temp = HPLChipcon.read(CC1K_CAL);
		} while(temp[CC1K_CAL_COMPLETE] == 0b0);

		// exit cal mode
		HPLChipcon.write(CC1K_CAL, ((1<<CC1K_CAL_WAIT) | (6<<CC1K_CAL_ITERATE))::8);
		
		// TOSH_uwait(200);
		return true;
	}

	method cc1000SetFreq() {
		local mask: 8;
		local temp_value: 8;
		local i: int;
		// FREQA, FREQB, FSEP, CURRENT(RX), FRONT_END, POWER, PLL
		for (i = 1; i < 0x0d::int; i++) {
			mask = MCU.disable();
			temp_value = gCurrentParameters[i];
			MCU.restore(mask);
			HPLChipcon.write(i::8, temp_value);
		}

		// MATCH
		mask = MCU.disable();
		temp_value = gCurrentParameters[0x12::int];
		MCU.restore(mask);
	
		HPLChipcon.write(CC1K_MATCH,temp_value);
		chipcon_cal();
	}

	method cc1000SetModem() {
		local mask: 8;
		local temp_value: 8;

		mask = MCU.disable();
		temp_value = gCurrentParameters[0x0f::int];
		MCU.restore(mask);
		HPLChipcon.write(CC1K_MODEM2,temp_value);

		mask = MCU.disable();
		temp_value = gCurrentParameters[0x10::int];
		MCU.restore(mask);
		HPLChipcon.write(CC1K_MODEM1,temp_value);

		mask = MCU.disable();
		temp_value = gCurrentParameters[0x11::int];
		MCU.restore(mask);
		HPLChipcon.write(CC1K_MODEM0,temp_value);
	}

	/*
	 * cc1000ComputeFreq(u3232_t desiredFreq);
	 *
	 * Compute an achievable frequency and the necessary CC1K parameters from
	 * a given desired frequency (Hz). The function returns the actual achieved
	 * channel frequency in Hz.
	 *
	 * This routine assumes the following:
	 *	- Crystal Freq: 14.7456 MHz
	 *	- LO Injection: High
	 *	- Separation: 64 KHz
	 *	- IF: 150 KHz
	 */
	method cc1000ComputeFreq(desiredFreq: int): 32 {
		local mask: 8;

		local ActualChannel: 32 = 0::32;
		local RXFreq: 32 = 0::32;
		local TXFreq: 32 = 0::32;
		local Offset: int = 0x7fffffff::int;
		local FSep: int = 0;
		local RefDiv: 8 = 0::8;
		local i: int;
	
		for (i = 0; i < 9; i++) {
			local NRef: int;
			local FRef: int = FRefTbl[i];
			local Channel: int = 0;
			local RXCalc: int = 0;
			local TXCalc: int = 0;
			local diff: int;

			NRef = (((desiredFreq + IF) * 4) / (FRef));
			if ((NRef & 0x1) != 0) {
			 	NRef++;
			}

			if ((NRef & 0x2) != 0) {
				RXCalc = 16384 / 2;
				Channel = FRef / 2;
			}

			NRef /= 4;

			RXCalc += (NRef * 16384) - 8192;
			if ((RXCalc < FREQ_MIN) or (RXCalc > FREQ_MAX)) continue;
		
			TXCalc = RXCalc - CorTbl[i];
			if ((TXCalc < FREQ_MIN) or (TXCalc > FREQ_MAX)) continue;

			Channel += (NRef * FRef);
			Channel -= IF;

			diff = Channel - desiredFreq;
			if (diff < 0) diff = 0 - diff;

			if (diff < Offset) {
				RXFreq = RXCalc;
				TXFreq = TXCalc;
				ActualChannel = Channel;
				FSep = FSepTbl[i];
				RefDiv = (i + 6)::8;
				Offset = diff;
			}
		}

		if (RefDiv != 0) {
			mask = MCU.disable();
			// FREQA
			gCurrentParameters[0x3::int] = ((RXFreq) & 0xFF)::8;	// LSB
			gCurrentParameters[0x2::int] = ((RXFreq >> 8) & 0xFF)::8;
			gCurrentParameters[0x1::int] = ((RXFreq >> 16) & 0xFF)::8;	// MSB
			// FREQB
			gCurrentParameters[0x6::int] = ((TXFreq) & 0xFF)::8; // LSB
			gCurrentParameters[0x5::int] = ((TXFreq >> 8) & 0xFF)::8;
			gCurrentParameters[0x4::int] = ((TXFreq >> 16) & 0xFF)::8;	// MSB
			// FSEP
			gCurrentParameters[0x8::int] = ((FSep) & 0xFF)::8;	// LSB
			gCurrentParameters[0x7::int] = ((FSep >> 8) & 0xFF)::8; //MSB

			MCU.restore(mask);

			if (ActualChannel::int < 500000000) {
				if (ActualChannel::int < 400000000) {
					mask = MCU.disable();
					// CURRENT (RX)
					gCurrentParameters[0x9::int] = (((8 << CC1K_VCO_CURRENT) | (1 << CC1K_LO_DRIVE)))::8;
					// CURRENT (TX)
					gCurrentParameters[0x1d::int] =( ((9 << CC1K_VCO_CURRENT) | (1 << CC1K_PA_DRIVE)))::8;
					MCU.restore(mask);
				} else {
					mask = MCU.disable();
					// CURRENT (RX)
					gCurrentParameters[0x9::int] = (((4 << CC1K_VCO_CURRENT) | (1 << CC1K_LO_DRIVE)))::8;
					// CURRENT (TX)
					gCurrentParameters[0x1d::int] = (((8 << CC1K_VCO_CURRENT) | (1 << CC1K_PA_DRIVE)))::8;
					MCU.restore(mask);
				}
				mask = MCU.disable();
				// FRONT_END
				gCurrentParameters[0xa::int] = (1 << CC1K_IF_RSSI)::8; 
				// MATCH
				gCurrentParameters[0x12::int] = (7 << CC1K_RX_MATCH)::8;
				MCU.restore(mask);
			} else {
				mask = MCU.disable();
				// CURRENT (RX)
				gCurrentParameters[0x9::int] = (((8 << CC1K_VCO_CURRENT) | (3 << CC1K_LO_DRIVE)))::8;
				// CURRENT (TX)
				gCurrentParameters[0x1d::int] =( ((15 << CC1K_VCO_CURRENT) | (3 << CC1K_PA_DRIVE)))::8;

				// FRONT_END
				gCurrentParameters[0xa::int] = ((1<<CC1K_BUF_CURRENT) | (2<<CC1K_LNA_CURRENT) | (1<<CC1K_IF_RSSI))::8;
				// MATCH
				gCurrentParameters[0x12::int] = (2 << CC1K_RX_MATCH)::8;
				MCU.restore(mask);
			}
			mask = MCU.disable();
			// PLL
			gCurrentParameters[0xc::int] = (RefDiv << CC1K_REFDIV)::8;
			MCU.restore(mask);
		}

		gCurrentChannel = ActualChannel;
		return ActualChannel;
	}


	method init(): boolean {
		local mask: 8;
		local temp_value: 32;

		HPLChipcon.init();

		// wake up xtal and reset unit
		HPLChipcon.write(CC1K_MAIN,
				((1 << CC1K_RX_PD) | (1 << CC1K_TX_PD) | 
				 (1 << CC1K_FS_PD) | (1 << CC1K_BIAS_PD))::8); 
		// clear reset.
		HPLChipcon.write(CC1K_MAIN,
				((1 << CC1K_RX_PD) | (1 << CC1K_TX_PD) | 
				 (1 << CC1K_FS_PD) | (1 << CC1K_BIAS_PD) |
				 (1 << CC1K_RESET_N))::8); 
		// reset wait time
		TOSH_uwait(2000);				

		mask = MCU.disable();
		// Set default parameter values
		// POWER 0dbm
		gCurrentParameters[0xb::int] = ((8 << CC1K_PA_HIGHPOWER) | (0 << CC1K_PA_LOWPOWER))::8; 
		temp_value = gCurrentParameters[0xb::int];
		MCU.restore(mask);
		HPLChipcon.write(CC1K_PA_POW, temp_value::8);

		mask = MCU.disable();
		// LOCK Manchester Violation default
		gCurrentParameters[0xd::int] = (9 << CC1K_LOCK_SELECT)::8;
		temp_value = gCurrentParameters[0xd::int];
		MCU.restore(mask);

		HPLChipcon.write(CC1K_LOCK, temp_value::8);

		mask = MCU.disable();
		// Default modem values = 19.2 Kbps (38.4 kBaud), Manchester encoded
		// MODEM2
		gCurrentParameters[0xf::int] = 0::8;
		//call HPLChipcon.write(CC1K_MODEM2,gCurrentParameters[0xf]);
		// MODEM1
		gCurrentParameters[0x10::int] = ((3 << CC1K_MLIMIT) | (1 << CC1K_LOCK_AVG_MODE) |(3 << CC1K_SETTLING) | (1 << CC1K_MODEM_RESET_N))::8;
		//call HPLChipcon.write(CC1K_MODEM1,gCurrentParameters[0x10]);
		// MODEM0
		gCurrentParameters[0x11::int] = ((5 << CC1K_BAUDRATE) | (1 << CC1K_DATA_FORMAT) |	(1 << CC1K_XOSC_FREQ))::8;
		MCU.restore(mask);

		cc1000SetModem();

		mask = MCU.disable();
		// FSCTRL
		gCurrentParameters[0x13::int] = (1 << CC1K_FS_RESET_N)::8;
		temp_value = gCurrentParameters[0x13::int];
		MCU.restore(mask);

		HPLChipcon.write(CC1K_FSCTRL,temp_value::8);

		mask = MCU.disable();
		// HIGH Side LO
		gCurrentParameters[0x1e::int] = 1::8;
		MCU.restore(mask);


		// Program registers w/ default freq and calibrate
		TunePreset(CC1K_DEF_PRESET); 		// go to default tune frequency
		return true;
	}


	method TunePreset(freq: 8): boolean {
		local i: int;
		local mask: 8;

		mask = MCU.disable();
		for (i = 1; i < 31 /*0x14*/; i++) {
			gCurrentParameters[i] = (CC1K_Params[freq::int][i])::8;
		}
		MCU.restore(mask);
		cc1000SetFreq();

		return true;
	}

	method TuneManual(DesiredFreq: int): 32 {
		local actualFreq: 32;

		actualFreq = cc1000ComputeFreq(DesiredFreq);

		cc1000SetFreq();

		return actualFreq;
	}

	method TxMode(): boolean {
		local mask: 8;
		local temp_value: 32;
		// MAIN register to TX mode
		HPLChipcon.write(CC1K_MAIN,
				((1<<CC1K_RXTX) | (1<<CC1K_F_REG) | (1<<CC1K_RX_PD) | 
				 (1<<CC1K_RESET_N))::8);
		// Set the TX mode VCO Current
		mask = MCU.disable();
		temp_value = gCurrentParameters[29];
		MCU.restore(mask);

		HPLChipcon.write(CC1K_CURRENT, temp_value::8);
		TOSH_uwait(250);
		
		mask = MCU.disable();
		temp_value = gCurrentParameters[0x0b::int];
		MCU.restore(mask);

		HPLChipcon.write(CC1K_PA_POW, temp_value::8 /*rfpower*/);
		TOSH_uwait(20);
		return true;
	}

	method RxMode(): boolean {
		local mask: 8;
		local temp_value: 32;
		// MAIN register to RX mode
		// Powerup Freqency Synthesizer and Receiver
		HPLChipcon.write(CC1K_MAIN,
				((1 << CC1K_TX_PD) | (1 << CC1K_RESET_N))::8);
		// Set the RX mode VCO Current
		mask = MCU.disable();
		temp_value = gCurrentParameters[0x09::int];
		MCU.restore(mask);
		HPLChipcon.write(CC1K_CURRENT, temp_value::8);
		HPLChipcon.write(CC1K_PA_POW, 0x00::8); // turn off power amp
		TOSH_uwait(250);
		return true;
	}

	method BIASOff(): boolean {
		// MAIN register to SLEEP mode
		HPLChipcon.write(CC1K_MAIN,
				((1 << CC1K_RX_PD) | (1 << CC1K_TX_PD) | 
				 (1 << CC1K_FS_PD) | (1 << CC1K_BIAS_PD) |
				 (1 << CC1K_RESET_N))::8);
								 
		return true;
	}

	method BIASOn(): boolean {
		//call CC1000Control.RxMode();
		HPLChipcon.write(CC1K_MAIN,
				((1 << CC1K_RX_PD) | (1 << CC1K_TX_PD) | 
				 (1 << CC1K_FS_PD) | 
				 (1 << CC1K_RESET_N))::8);
		
		TOSH_uwait(200 /*500*/);
		return true;
	}


	method stop(): boolean {
		// MAIN register to power down mode. Shut everything off
		HPLChipcon.write(CC1K_PA_POW, 0x00);	// turn off rf amp
		HPLChipcon.write(CC1K_MAIN,
				((1 << CC1K_RX_PD) | (1 << CC1K_TX_PD) | 
				 (1 << CC1K_FS_PD) | (1 << CC1K_CORE_PD) | (1 << CC1K_BIAS_PD) |
				 (1 << CC1K_RESET_N))::8);

		return true;
	}

	method start(): boolean {
		// wake up xtal osc
		HPLChipcon.write(CC1K_MAIN,
				((1 << CC1K_RX_PD) | (1 << CC1K_TX_PD) | 
				 (1 << CC1K_FS_PD) | (1 << CC1K_BIAS_PD) |
				 (1 << CC1K_RESET_N))::8);

		TOSH_uwait(2000);
		return true;
	}


	method SetRFPower(power: 8): boolean {
		local mask:8;
		mask = MCU.disable();
		gCurrentParameters[0x0b::int] = power;
		MCU.restore(mask);
		return true;
	}

	method GetRFPower(): 32 {
		return gCurrentParameters[0x0b::int]; // rfpower;
	}

	method SelectLock(Value: 8): boolean {
		local mask: 8;

		// TODO: byte store is atomic, no need to disable here
		mask = MCU.disable();
		gCurrentParameters[0x0d::int] = (Value << CC1K_LOCK_SELECT)::8;
		MCU.restore(mask);

		return HPLChipcon.write(CC1K_LOCK, (Value << CC1K_LOCK_SELECT)::8);
	}

	method GetLock(): 8 {
		local retVal:8;
		retVal = HPLChipcon.GetLOCK() ::8; 
		return retVal;
	}

	method GetLOStatus(): boolean {
		if(gCurrentParameters[0x1e::int] == 0) return false;
		else return true;
	}

}


