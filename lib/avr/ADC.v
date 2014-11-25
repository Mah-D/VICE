/**
 * ADC (analog to digital) conversion driver.
 * @author Ryan Hall, Akop Palyan
 * @ modified Xiaoli Gong Feb 20 ,2009
 * 1. disable the ADC after convert is over
 * 2. check if there is a convert in processing
 */
component ADC {
	field ADEN: 8 = 0x80;
	field ADSC: 8 = 0x40;
	field ADFR: 8 = 0x20;
	field ADIF: 8 = 0x10;
	field ADIE: 8 = 0x08;
	field ADPS2: 8 = 0x04;
	field ADPS1: 8 = 0x02;
	field ADPS0: 8 = 0x01;

	field convFunc: function(10) = none;
	field busy:boolean = false;
	method enable() {
		// enable ADC by setting ADEN bit
		device.ADCSRA = device.ADCSRA | ADEN;
		//device.ADCSRA = device.ADCSRA | ADFR;
		device.ADCSRA = device.ADCSRA | ADIE;
		device.ADMUX = 0x0;
	}

	method disable() {
		while(busy);
		busy = false;
		// disable ADC by clearing ADEN bit
		device.ADCSRA = device.ADCSRA & ~ADEN;
	}

	method startConv(channel: int, freeRunning: boolean) {

		if(busy) return;
		// start ADC conversion
		enable();
		device.ADMUX = channel :: 8;
		device.ADCSRA = device.ADCSRA | ADSC;
		busy=true;
	}

	method setConvFunc(f: function(10)) {
		convFunc = f;
	}

	method setPrescaler(val: 3) {
		device.ADCSRA = device.ADCSRA & 0xf8 | val;
	}

	method convHandler() {
		local low: 8 = device.ADCL;
		local high: 8 = device.ADCH;
		local value: 10 = (high # low) :: 10;
		busy=false;
		disable();////clean the convert in processing
		convFunc(value);
	}

	method none(value: 10) { }
}
