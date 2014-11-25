component MCU {
    field OCF0: int = 1; // bit number for output compare flag

	method sleep() {
		device.SREG = (0b10000000); // enable interrupts
		device.MCUCR = 0x00;
		device.sleep();
	}

	method sleepForever() {
		while (true) sleep();
	}

	method disable(): 8 {
		local mask = device.SREG;
		device.SREG[7] = 0b0;
		return mask;
	}

	method restore(mask: 8) {
		device.SREG = mask;
	}

	method enable() {
		device.SREG = 0b10000000;
	}

	method wait(time: int) {
		while (time-- > 0) wait_ms();
	}

	method wait_ms() {
		local mask = disable();

		//------( interrupts disabled )-----------
		local tifr = device.TIFR;
		local counter = Timer0.getCount();	// save current count
		local divider = Timer0.divider;	// save current divider

		device.TIFR[OCF0] = 0b1; // clear compare flag
		Timer0.setCount(device.OCR0 :: int - 1);
		if (divider != Timer0.DIV_32)
			Timer0.setDivider(Timer0.DIV_32);

		while (device.TIFR[OCF0] != 0b1) {
			// wait until match occurs
		}

		Timer0.setCount(counter);		// timer current count
		if (divider != Timer0.DIV_32)
			Timer0.setDivider(divider); // restore current divider
		device.TIFR = tifr;			 // restore interrupt flags
		//------( interrupts re-enabled )-----------

		restore(mask);		// enable interrupts
	}

	method sleepTransfer(buf: char[], pos: int, len: int, tx: function(char[], int, int): int) {
		local max = pos + len;
		while ( true ) {
			pos = pos + tx(buf, pos, max - pos);
			if ( pos >= max ) break;
			else sleep();
		}
	}

	method busyTransfer(buf: char[], pos: int, len: int, tx: function(char[], int, int): int) {
		local max = pos + len;
		while ( pos < max ) {
			pos += tx(buf, pos, max - pos);
		}
	}
}

