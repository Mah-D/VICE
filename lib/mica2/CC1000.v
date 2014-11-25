/*
 * CC1000 Radio driver.
 * @author Akop Palyan
 */
component CC1000 {
	// PORT B
	field CHP_OUT: 3 = 06;

	// PORT D
	field PALE: 3 = 04;
	field PCLK: 3 = 06;
	field PDATA: 3 = 07;

	// CC1000 Register addresses
	field MAIN: 8 = 0x00;
	field FREQ_2A: 8 = 0x01;
	field FREQ_1A: 8 = 0x02;
	field FREQ_0A: 8 = 0x03;
	field FREQ_2B: 8 = 0x04;
	field FREQ_1B: 8 = 0x05;
	field FREQ_0B: 8 = 0x06;
	field FSEP1: 8 = 0x07;
	field FSEP0: 8 = 0x08;
	field CURRENT: 8 = 0x09;
	field FRONT_END: 8 = 0x0A;
	field PA_POW: 8 = 0x0B;
	field PLL: 8 = 0x0C;
	field LOCK: 8 = 0x0D;
	field CAL: 8 = 0x0E;
	field MODEM2: 8 = 0x0F;
	field MODEM1: 8 = 0x10;
	field MODEM0: 8 = 0x11;
	field MATCH: 8 = 0x12;
	field FSCTRL: 8 = 0x13;
	field FSHAPE7: 8 = 0x14;
	field FSHAPE6: 8 = 0x15;
	field FSHAPE5: 8 = 0x16;
	field FSHAPE4: 8 = 0x17;
	field FSHAPE3: 8 = 0x18;
	field FSHAPE2: 8 = 0x19;
	field FSHAPE1: 8 = 0x1A;
	field FSDELAY: 8 = 0x1B;
	field PRESCALER: 8 = 0x1C;
	field TEST6: 8 = 0x40;
	field TEST5: 8 = 0x41;
	field TEST4: 8 = 0x42;
	field TEST3: 8 = 0x43;
	field TEST2: 8 = 0x44;
	field TEST1: 8 = 0x45;
	field TEST0: 8 = 0x46;

	// 'MAIN' register masks
	field RESET_N: 8 = 0x01;
	field BIAS_PD: 8 = 0x02;
	field CORE_PD: 8 = 0x04;
	field FS_PD: 8 = 0x08;
	field TX_PD: 8 = 0x10;
	field RX_PD: 8 = 0x20;
	field F_REG: 8 = 0x40;
	field RXTX: 8 = 0x80;

	// 'CURRENT' register masks
	field PA_DRIVE: 8 = 0x01;
	field LO_DRIVE: 8 = 0x02;
	field VCO_CURRENT: 8 = 0x10;

	// 'CAL' register masks
	field CAL_START: 8 = 0x80;
	field CAL_DUAL: 8 = 0x40;
	field CAL_WAIT: 8 = 0x20;
	field CAL_CURRENT: 8 = 0x10;
	field CAL_COMPLETE: 8 = 0x08;

	// Default Presets
	field PRESET0: 8[] = {
		0x58, 0x00, 0x00,	// FREQA (433 MHz channel)
		0x57, 0xF6, 0x85,	// FREQB
		0x03, 0x55,		// FSEP1, FSEP0
		0x44,			// CURRENT (RX mode)
		0x02,			// FRONT_END
		0x0F,			// PA_POWER
		0x60,			// PLL (REFDIV = 12)
		0x81			// CURRENT (TX mode);
	};            

	field parameters: 8[];

	// Initialization routine
	method init(params: 8[]) {
		local i: int;

		parameters = params;

		device.DDRB[CHP_OUT::int] = 0b0;
		device.DDRD[PALE::int] = 0b1;
		device.DDRD[PCLK::int] = 0b1;
		device.DDRD[PDATA::int] = 0b1;

		device.PORTD[PALE::int] = 0b1;
		device.PORTD[PDATA::int] = 0b1;
		device.PORTD[PCLK::int] = 0b1;

		write(MAIN, RX_PD | TX_PD | FS_PD | BIAS_PD); // reset
		write(MAIN, RX_PD | TX_PD | FS_PD | BIAS_PD | RESET_N); // clear reset

		// wait for 2ms
		MCU.wait(2);

		write(PA_POW, 0x80); // power at 0dbm
		write(LOCK, 0x90); // manchester violation

		// Default modem values: 19.2 Kbps, Manchester encoded
		write(MODEM2, 0x00);
		write(MODEM1, 0x6F);
		write(MODEM0, 0x55);

		write(FSCTRL, 0x01);

		for (i = 0; i < 12; i++) {
			write((i+1) :: 8, params[i]);
		}

		write(MATCH, 0x70);

		calibrate();
	}

	method calibrate() {
		write(PA_POW, 0x00);
		write(TEST4, 0x3f);

		write(MAIN, TX_PD | RESET_N); // power down TX
		write(CAL, CAL_START | CAL_WAIT | 0x6); // begin calibration

		while ((read(CAL) & CAL_COMPLETE) == 0x0) {
			// wait until completion.
		}

		write(CAL, CAL_WAIT | 0x06);

		write(MAIN, RXTX | F_REG | RX_PD | RESET_N); // power down RX and switch to TX

		write(CURRENT, parameters[12]);
		write(PA_POW, 0x00);

		write(CAL, CAL_START | CAL_WAIT | 0x06); // begin calibration

		while ((read(CAL) & CAL_COMPLETE) == 0x0) {
			// wait until completion.
		}

		write(CAL, CAL_WAIT | 0x06);
	}

	method start() {
		write(MAIN, RX_PD | TX_PD | FS_PD | BIAS_PD | RESET_N);
		MCU.wait(2);
	}

	method stop() {
		write(PA_POW, 0x00);
		write(MAIN, RX_PD | TX_PD | FS_PD | CORE_PD | BIAS_PD | RESET_N);
	}

	// Write 'value' to register at 'address'
	method write(address: 8, value: 8) {
		local i: int;

		device.PORTD[PALE::int] = 0b0;

		// Send 7-bit address
		sendMSB(address, 7);

		// Send read/write bit
		sendBit(0b1);

		device.PORTD[PALE::int] = 0b1;

		// Send 8-bit value by clocking 'PCLK'
		sendMSB(value, 8);

		device.PORTD[PALE::int] = 0b1;
		device.PORTD[PDATA::int] = 0b1;
		device.PORTD[PCLK::int] = 0b1;
	}

	// Read from register at 'address'
	method read(address: 8): 8 {
		local value: 8 = 0x0;
		local i: int;

		device.PORTD[PALE::int] = 0b0;

		// Send 7-bit address
		sendMSB(address, 7);

		// Send read/write bit
		sendBit(0b0);

		device.DDRD[PDATA::int] = 0b0;
		device.PORTD[PALE::int] = 0b1;

		// Receive 8-bit value by clocking 'PCLK'
		for (i = 0; i < 8; i++) {
			device.PORTD[PCLK::int] = 0b0;
			value = value << 1;
			if (device.PIND[PDATA::int] == 1) {
				value = value | 0x01;
			} else {
				value = value & 0xFE;
			}
			device.PORTD[PCLK::int] = 0b1;
		}

		device.PORTD[PALE::int] = 0b1;
		device.DDRD[PDATA::int] = 0b1;
		device.PORTD[PDATA::int] = 0b1;

		return value;
	}

	method sendMSB(val: 8, len: int) {
		// Send len bits starting with most significant
		while ( len > 0 ) sendBit(val[--len]);
	}

	method sendBit(bit: 1) {
		device.PORTD[PDATA::int] = bit;
		device.PORTD[PCLK::int] = 0b0;
		device.PORTD[PCLK::int] = 0b1;
	}

	// Switch to TX mode
	method txMode() {
		write(MAIN, RXTX | F_REG | RX_PD | RESET_N); // power down RX and switch to TX
		write(CURRENT, parameters[12]);
		write(PA_POW, parameters[10]);
	}

	// Switch to RX mode
	method rxMode() {
		write(MAIN, TX_PD | RESET_N); // power down TX and switch to RX
		write(CURRENT, parameters[8]);
		write(PA_POW, 0x0);
	}

	method selectLock(value: char) {
		write(LOCK, (value :: 8) << 4);
	}
}
