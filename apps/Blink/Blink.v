/**
 * The Blink program is a simple Mica2 application that simply turns the
 * LED on and off once per second. It configures the Timer0 device to
 * interrupt the processor twice per second and uses the interrupt routine
 * to toggle the red LED.
 *
 * @author Ben L. Titzer
 */
program Blink {
	entrypoint main = Blink.start;
	entrypoint timer0_comp = Timer0.compareHandler;
}

component Blink {
	constructor() {
		Mica2.setTimerCompare(Mica2.red.toggle); // set the function to call on overflow
	}

	method start() {
		Mica2.startLEDs();
		Mica2.startTimer();		   // start up the basic timer
		Power.enable();
		// MCU.sleepForever();
		if(true){
			MCU.enable();
			device.sleep();
		}
	}
}
