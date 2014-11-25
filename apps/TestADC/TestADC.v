/*
 * This program simply tests the functionality of the ADC (analog to digital)
 * converter driver.
 * @author Akop Palyan
 */
program TestADC {
	entrypoint main = TestADC.start;
	entrypoint adc = ADC.convHandler;
}

component TestADC {

	constructor() {
		ADC.setConvFunc(toLEDS);
	}

	method start() {
		Mica2.startLEDs();
		Mica2.startLightSensor();
		ADC.enable();
		ADC.setPrescaler(0b110);
		 
			ADC.startConv(Mica2.LIGHT_CHANNEL, true);
			MCU.sleep();
		
	}

	method toLEDS(value: 10) {
		Mica2.displayOnLEDs(value :: 3);
	}
}
