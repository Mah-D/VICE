program Oscilloscope {
	entrypoint main = Oscilloscope.main;
	entrypoint timer0_comp = Timer0.compareHandler;
	entrypoint adc = ADC.convHandler;

	entrypoint usart0_rx = USART0.rec_handler; // We don't use RX, but without this line I get a stack overflow.
//	entrypoint usart0_udre = USART0.tran_handler;
        entrypoint usart0_tx = USART0.tran_handler;
}

component Oscilloscope {
	// half-open range
	field AdcIndexBegin: int = 2;
	field AdcIndexEnd: int = 7;
	field AdcCount: int = AdcIndexEnd - AdcIndexBegin;

	field readings: 10[] = new 10[AdcCount];
	field t_buf: char[] = "Hello World\n";

	field working : boolean = false;
	field adcIndex: int = 0;
	field outBuf : char[] = new char[10];

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
		// Set a flag to avoid nested timer interrupts.
		if (working) return;
		working = true;

		Mica2.red.toggle();

		adcIndex = 0;
		ADC.startConv(AdcIndexBegin+adcIndex, true);
	}

	method readADC(value: 10) {
		readings[adcIndex] = value;

		adcIndex++;
		if (adcIndex < AdcCount) {
			ADC.startConv(AdcIndexBegin+adcIndex, true);
		} else {
			adcIndex = 0;
			sendReadings();
			
		}
	}

	method sendReadings() {
		local bufI = 0;
		local adcI = 0;
		while (adcI < AdcCount) {
			local v = readings[adcI];
			bufI = writeInt(outBuf, bufI, v :: int);
//			outBuf[bufI++] = (v >> 8) :: 8 :: char;
//			outBuf[bufI++] = (v & 0xf) :: 8 :: char;
			adcI++;
		}
		outBuf[bufI++] = '\n';

		MCU.sleepTransfer(outBuf, 0, bufI, USART0.transmit);
	//	USART0.transmit(outBuf,0,bufI);
		working = false;
	}

	method writeInt(buf: char[], bufI: int, value: int): int {
		buf[bufI++] = ' ';
		while(value <0)value +=256;
		if(true){
			local digit = ('0' :: int) + (value % 10);
			buf[bufI++] = digit :: char;
			value = value / 10;
			if (value == 0)
			    {}
		}
		return bufI;
	}
}
