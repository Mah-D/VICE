program Oscilloscope {
	entrypoint main = Oscilloscope.main;
	entrypoint timer0_comp = Timer0.compareHandler;
	entrypoint adc = ADC.convHandler;

	entrypoint usart0_rx = USART0.rec_handler; // We don't use RX, but without this line I get a stack overflow.
	entrypoint usart0_udre = USART0.tran_handler;
}

component Oscilloscope {
	field readings: 16[] = new 16[AdcCount];
	field t_buf: char[] = "Hello World\n";

	field working: boolean = false;
	field outBuf: char[] = new char[100];


	constructor() {
		ADC.setConvFunc(readADC);
		Mica2.setTimerCompare(startReadADC);
	}

	method main() {
		Mica2.startTerminal();
		ADC.enable();
		Mica2.startTimer();
		Timer0.setCompareMatch(128);

		MCU.sleepForever();
	}

	// This function is called periodically (timer interrupt).
	method startReadADC() {
		adcIndex = 0;
		ADC.startConv(1, true);
	}

	method readADC(value: 10) {
		sendReading(value);
	}

	method sendReading(value: 10) {
		// fill 'outBuf' with 'reading'.
		MCU.sleepTransfer(outBuf, 0, bufI, USART0.transmit);
	}
}
