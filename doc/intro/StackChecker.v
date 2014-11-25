/**
 * The Blink program is a simple Mica2 application that simply turns the
 * LED on and off once per second. It configures the Timer0 device to
 * interrupt the processor twice per second and uses the interrupt routine
 * to toggle the red LED.
 *
 * @author Ben L. Titzer
 */
program StackChecker{
    entrypoint main = StackChecker.start;
//    entrypoint timer0_comp = Timer0.compareHandler;
}

component StackChecker{
    constructor() {
//        Mica2.setTimerCompare(Mica2.red.toggle); // set the function to call on overflow
    }

	field i:int= 9;
	field j:int= 6;


	//-------------------------------------------------------------------------
	// foo()
	//   multiply j by 13 and return it
	//-------------------------------------------------------------------------
	method foo(m:int):int {
		local k:int;
		k =  m * j;
  		return k;
	}
    	method start(arg: char[][]) {
  		local i:int;

  		// call the foo() method a number of times
  		for ( i = 0; i < 5; i++ )
    			j = foo(i);
    	}
}
