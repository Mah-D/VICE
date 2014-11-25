/*
 * This program simply tests the functionality of the Radio driver.
 * @author Xiaoli Gong
 */
program TestRadio {
	entrypoint main = TestRadio.entry;
	entrypoint adc = ADC.convHandler;
	entrypoint spi_stc = SPI.interruptHandler;
	entrypoint timer0_comp = Timer0.compareHandler;
	entrypoint usart0_rx = USART0.rec_handler;
	entrypoint usart0_tx = USART0.tran_handler;
}

component TestRadio {

	field flag: boolean;
	field msg: TOS_Msg;
	field msg_sent: char[] = "One Packet Sent :\n";
	field msg_addr: char[] = "Packet Addr:\n";
	field msg_data: char[] = "Packet Data:\n";
	field counter: int = 0;
	field busy: boolean = false;

	constructor() {
		msg = new TOS_Msg();
		msg.addr = 0xffff;
		msg.type = 0x04;
		msg.group = 0x7d;
		msg.length = 0x04;
		msg.data[0] = 0x5d;
		msg.data[1] = 0x01;
		msg.data[2] = 0x01;
		msg.data[3] = 0x00;
	}

	method sendDone(data: TOS_Msg, result: boolean) {
		local i: int = 0;
		flag = true;
		counter++;

		if (counter % 50 == 0) {
			USART0.printString(msg_sent, msg_sent.length);
			USART0.printString(msg_addr, msg_addr.length);
			USART0.printHex16(msg.addr);
			USART0.printString(msg_data, msg_data.length);
			for (i = 0; i < msg.length::int; i++)
				USART0.printHex8(msg.data[i]);
		}

		msg.data[0] = (msg.data[0]::int + 1)::8;
		msg.data[1] = (msg.data[1]::int + 1)::8;
		busy = false;
	}

	method receivedata(data: TOS_Msg) {
		Mica2.red.toggle();
		Mica2.yellow.toggle();
		Mica2.green.toggle();
	}

	method entry() {
		Mica2.startLEDs();
		Mica2.startTerminal();
		// Mica2.red.toggle();
		CC1000Radio.init();
		CC1000Radio.start();
		CC1000Radio.SetsendDoneCallback(sendDone);
		if (counter < 10){
			busy = true;
			CC1000Radio.send(msg);
			// MCU.sleepForever();
			//while(busy);
		}
		CC1000Radio.stop();
	}

}
