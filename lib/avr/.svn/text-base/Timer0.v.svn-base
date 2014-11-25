component Timer0 {

	// binary values for the mode field
	field MODE_NORMAL: 2   = 0b00;
	field MODE_CTC: 2	  = 0b10;
	field MODE_FAST_PWM: 2 = 0b11;
	field MODE_PC_PWM: 2   = 0b01;

	// octal values for the divider field
	field DIV_1: 3	= 01;
	field DIV_8: 3	= 02;
	field DIV_32: 3   = 03;
	field DIV_64: 3   = 04;
	field DIV_128: 3  = 05;
	field DIV_256: 3  = 06;
	field DIV_1024: 3 = 07;

	field overflowFunc: function() = none;
	field compareFunc: function() = none;
	field divider: 3;

	method enable() { 
		// set lower three bits
		device.TCCR0 = device.TCCR0 | divider; 
	}

	method disable() { 
		// clear lower 3 bits
		device.TCCR0 = device.TCCR0 & 0b11111000; 
	} 

	method setMode(m: 2) {
		local tccr: 8 = device.TCCR0;
		tccr[6] = m[0];
		tccr[3] = m[1];
		device.TCCR0 = tccr;
	}

	method setDivider(div: 3) { 
		divider = div;
		device.TCCR0 = (device.TCCR0 & 0b11111000) | div;
	}

	method setCompareMatch(matchVal: int) { 
		device.OCR0 = matchVal :: 8; 
	}

	method setCount(count: int) { 
		device.TCNT0 = count :: 8; 
	}

	method getCount(): int { 
		return device.TCNT0 :: int; 
	}

	method setOverflow(of: function()) { 
		overflowFunc = of; 
	}

	method setCompare(cf: function()) { 
		compareFunc = cf; 
	}

	method overflowHandler() { 
		overflowFunc(); 
	}

	method compareHandler() { 
		compareFunc(); 
		Power.adjustPower();
	}

	method none() { 
		// do nothing.
	}
}
