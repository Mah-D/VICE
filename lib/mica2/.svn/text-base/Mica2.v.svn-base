component Mica2 {

	field portA: PortA = new PortA();
	field portB: PortB = new PortB();
	field portC: PortC = new PortC();
	field portD: PortD = new PortD();
	field portE: PortE = new PortE();

	field green: LED  = new LED(new Pin(portA, 1), "Green");
	field yellow: LED = new LED(new Pin(portA, 0), "Yellow");
	field red: LED	= new LED(new Pin(portA, 2), "Red");

	field LIGHT_CHANNEL: int = 1;		// ADC channel for light sensor
	field MAIN_CLOCK_HZ: int = 7372800;	// main clock rate
	field EXT_CLOCK_HZ:  int = 32768;	// external clock rate

	method startTimer() {
		device.ASSR = 0b1000;			// set Timer0 to asynch operation
		Timer0.setMode(Timer0.MODE_CTC);	// use clear timer on compare mode
		Timer0.setCompareMatch(16);		// compare matches every (16 * 1024) cycles
		Timer0.setCompareMatch(32);		// compare matches every (16 * 1024) cycles
		Timer0.setCount(0);			// reset the count
		device.TIMSK = 0b10;			// enable the Timer0 compare interrupt
		Timer0.setDivider(Timer0.DIV_1024);	// set divider to 1024
		Power.adjustPower();
	}

	method startSPI(master: boolean) {
		if ( master ) {
			portB.setDirection(0x6);
			SPI.startMaster();
		} else {
			portB.setDirection(0x8);
			SPI.startSlave();
		}
	}

	method startLightSensor() {
		portC.setOutput();
		portE.setOutput();
		portE.out(0b00100000);
	}

	method startTerminal() {
	//	USART0.enable(calculateBaud(4800, 2), true, false);
		USART0.enable(calculateBaud(57600, 2), false, true);
		MCU.enable();
	}

	method startLEDs() {
		portA.setOutput();
		portA.out(0xff);
	}

	method setTimerCompare(cf: function()) {
		Timer0.setCompare(cf);
	}

	method displayOnLEDs(value: 3) {
		displayLED(red, value[0]);
		displayLED(yellow, value[1]);
		displayLED(green, value[2]);
	}

	method displayLED(led: LED, b: 1) {
		if ( b == 0b1 ) led.on();
		else led.off();
	}

	method calculateBaud(rate: int, mode: int): int {
		return USART.calculateBaud(MAIN_CLOCK_HZ, rate, mode);
	}
}
