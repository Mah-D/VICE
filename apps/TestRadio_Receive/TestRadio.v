/*
 * This program simply tests the functionality of the Radio (wireless signal )
 *  driver.
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
	field msg_sent: char[] = "One Packet received:\n";
	field msg_addr: char[] = "Packet Addr:\n";
	field msg_data: char[] = "Packet Data:\n";
	field counter: int = 0;

	constructor() {
		msg = new TOS_Msg();
		msg.length = 0x05;
		msg.data[0] = 0x33;
		msg.data[1] = 0xcc;
		msg.data[2] = 0x03;
		msg.data[3] = 0x04;
		msg.data[4] = 0x05;
		// CC1000Radio.SetsendDoneCallback(sendbusy);
	}

	method sendbusy(data: TOS_Msg, result: boolean) {
		//CC1000Radio.WakeupTimerfired();
	}

	method receivedata(data:TOS_Msg): TOS_Msg {
		local i: int = 0;
		counter++;
		Mica2.displayOnLEDs(counter::3);
		if (counter % 50 == 0){
			USART0.printString(msg_sent, msg_sent.length);
			USART0.printString(msg_addr, msg_addr.length);
			USART0.printHex16(msg.addr);
			USART0.printString(msg_data, msg_data.length);
			for (i = 0; i < msg.length::int; i++)
				USART0.printHex8(msg.data[i]);
		}
		return data;
	}

	method entry() {
		local i: int = 0;
		Mica2.startLEDs();
		Mica2.startTerminal();
		CC1000Radio.init();
		CC1000Radio.SetsendDoneCallback(sendbusy);
		CC1000Radio.SetReceiveCallback(receivedata);
		CC1000Radio.start();
		if (i < 1) {
			i++;
			MCU.enable();
			device.sleep();
		}
	}

}
