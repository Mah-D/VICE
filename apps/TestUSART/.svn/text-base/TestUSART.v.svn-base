/*
 * This program tests the operation of the USART serial driver.
 * @author Xiaoli Gong
 */
program TestUSART {
	entrypoint main = TestUSART.entry;
	entrypoint usart0_rx = USART0.rec_handler;
	entrypoint timer0_comp = Timer0.compareHandler;
	entrypoint usart0_tx = USART0.tran_handler;
}

component TestUSART {
	field t_buf: char[] = "ABCcccccDEFGHIJK\n";
	field i: int = 0;

	method print(){
		USART0.transmit(t_buf, 0, t_buf.length);
	}

	method entry() {
		Mica2.setTimerCompare(print); // set the function to call on overflow
		Mica2.startLEDs();
		Mica2.startTerminal();
 	    
		// Mica2.red.toggle();
		Mica2.startTimer();           // start up the basic timer
		MCU.sleep();
	}
}
