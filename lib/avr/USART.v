/**
 * USART driver.
 * @author Ryan Hall
 * @ modified by Xiaoli Gong Feb 20, 2009
 * @ change the initial configuration of the port
 */
component USART {

	// bit names for UCSRnA register
	field RXCn:  8 = 0x80;  // USART receive complete
	field TXCn:  8 = 0x40;  // USART transmit complete
	field UDREn: 8 = 0x20;  // USART data registe empty
	field FEn:   8 = 0x10;  // Frame error : set to 1 when writing to UCSRnA
	field DORn:  8 = 0x08;  // Data over run : set to 0 when writing to UCSRnA
	field UPEn:  8 = 0x04;  // Parity error : set to 0 when writing to UCSRnA
	field U2Xn:  8 = 0x02;  // Double USART transmission speed : set to 0 in sync mode
	field MPCMn: 8 = 0x01;  // Multi-processor communication mode

	// bit names for UCSRnB register
	field RXCIEn: 8 = 0x80;  // enable receive complete interrupt
	field TXCIEn: 8 = 0x40;  // enable transmission complete interrupt
	field UDRIEn: 8 = 0x20;  // enable data register empty interrupt
	field RXENn:  8 = 0x10;  // enable receiver
	field TXENn:  8 = 0x08;  // enable transmitter
	field UCSZn2: 8 = 0x04;  // high end bit of 3 for frame size
	field RXB8n:  8 = 0x02;  // 9th bit of received character
	field TXB8n:  8 = 0x01;  // 9th bit of transmitted character

	// bit names for UCSRnC register
	field UMSELn: 8 = 0x40;  // 0 for async, 1 for sync mode
	field UPMn1:  8 = 0x20;  // Parity mode
	field UPMn0:  8 = 0x10;  // Parity mode
	field USBSn:  8 = 0x08;  // Number of stop bits inserted by transmitter
	field UCSZn1: 8 = 0x04;  // Character size low 2 bits
	field UCSZn0: 8 = 0x02;  // Character size low 2 bits
	field UCPOLn: 8 = 0x01;  // Clock polarity in sync mode

	// Baud Rate Registers
	// UBRRnL : lower 8 bits of baud rate
	// UBRRnH : higher 4 bits of baud rate

	// Data Register
	// UDRn

	method calculateBaud(fosc: int, rate: int, mode: int): int {
	if ( mode == 0 ) return fosc / (16 * rate) - 1;
	if ( mode == 1 ) return fosc / (8 * rate) - 1;
	if ( mode == 2 ) return fosc / (2 * rate) - 1;
	return 0;
	}
}

component USART0 {

	//bit names for UCSR0A register
	field RXC0: 8 = 0x80;   // USART receive complete
	field TXC0: 8 = 0x40;   // USART transmit complete
	field UDRE0: 8 = 0x20;  // USART data registe empty
	field FE0: 8 = 0x10;    // Frame error : set to 1 when writing to UCSR0A
	field DOR0: 8 = 0x08;   // Data over run : set to 0 when writing to UCSR0A
	field UPE0: 8 = 0x04;   // Parity error : set to 0 when writing to UCSR0A
	field U2X0: 8 = 0x02;   // Double USART transmission speed : set to 0 in sync mode
	field MPCM0: 8 = 0x01;  // Multi-processor communication mode

	//bit names for UCSR0B register
	field RXCIE0: 8 = 0x80; // enable receive complete interrupt
	field TXCIE0: 8 = 0x40; // enable transmission complete interrupt
	field UDRIE0: 8 = 0x20; // enable data register empty interrupt
	field RXEN0: 8 = 0x10;  // enable receiver
	field TXEN0: 8 = 0x08;  // enable transmitter
	field UCSZ02: 8 = 0x04; // high end bit of 3 for frame size
	field RXB80: 8 = 0x02;  // 9th bit of received character
	field TXB80: 8 = 0x01;  // 9th bit of transmitted character

	//bit names for UCSR0C register
	field UMSEL0: 8 = 0x40;  // 0 for async, 1 for sync mode
	field UPM01: 8 = 0x20;   // Parity mode
	field UPM00: 8 = 0x10;   // Parity mode
	field USBS0: 8 = 0x08;   // Number of stop bits inserted by transmitter
	field UCSZ01: 8 = 0x04;  // Charcter size low 2 bits
	field UCSZ00: 8 = 0x02;  // Charcter size low 2 bits
	field UCPOL0: 8 = 0x01;  // Clock polarity in sync mode

	field r_queue : Queue<char>;  // receive queue
	field t_queue : Queue<char>;  // transmit queue
 
	field checksum: CRC;
	field printBuf: char[];
	field packetbuf:char[];
	field runningCRC:16 = 0x0000;
	field usingCRC:boolean = false;

	constructor() {
		r_queue = new Queue<char>(50);
		t_queue = new Queue<char>(127);
		checksum = new CRC();

		printBuf = new char [10];
		packetbuf = new char [40];
	}

	method enable(baud_rate: int, sync_mode : boolean, double_async : boolean) {
		// set character size to 8
		device.UCSR0C = UCSZ01 | UCSZ00;
		if (sync_mode) {
			device.UCSR0C = device.UCSR0C | UMSEL0;
		} else if (double_async) {
			device.UCSR0A = U2X0;
		}
		// set baud rate
		device.UBRR0H = (baud_rate >> 8) :: 8;
		device.UBRR0L = (baud_rate & 0xFF) :: 8;
		///////////for test just set baud rate as 56.7K
		device.UBRR0H = 0x00;
		device.UBRR0L = 15::8; 

		// enable interrupts, receiver and transmitter
		device.UCSR0B = RXEN0 | TXEN0 | RXCIE0;
		Power.adjustPower();
	}

	method disable() {
		// disable transmitter and receiver
		device.UCSR0B = 0x0;
		Power.adjustPower();
	}

	method transmit(buf : char[], i : int, l : int) : int {
		local index : int = 0;
		local mask = MCU.disable();

		if (t_queue.size() + l + 5 >127) {
			MCU.restore(mask);
			return 0;
		}
		if (usingCRC == true) {
			t_queue.enqueue(0x7e::char);//////start
			runningCRC = checksum.crcByte(runningCRC, 66::8);
			t_queue.enqueue(66::char);
		}
		for (index = 0 ; index < l ; index++) {

		if (buf[i + index] == 0x7e::char or 
			buf[i + index] == 0x7d::char) {
			t_queue.enqueue(0x7d::char);
			t_queue.enqueue(((buf[i + index])^(0x20))::char);
		} else {
			t_queue.enqueue(buf[i + index]);
		}
		if (usingCRC)
			runningCRC = checksum.crcByte(runningCRC,buf[i + index]);
		}
		if (usingCRC) {
			t_queue.enqueue((runningCRC&0xff)::char);
			t_queue.enqueue((((runningCRC)>>8)::8)::char);
			t_queue.enqueue(0x7e::char);
			runningCRC = 0x0000;
		}
		MCU.restore(mask);
		if (t_queue.size() > 0) {
			// turn on data ready interrupts
			device.UCSR0B |= RXEN0 | TXEN0 | TXCIE0| RXCIE0;
			tran_handler();
		}
		return index;	
	}

	method setCRCMode(mode:boolean) {
		usingCRC = mode;
	}

	method tran_handler() {
		local mask=MCU.disable();
		local cc:char ='\0';
		if (t_queue.size() > 0) {
			cc = t_queue.dequeue();
		} else {
			MCU.restore(mask);
			return;
		}

		device.UDR0 = cc;
		device.UCSR0A[6] = 0b1;
		MCU.restore(mask);
		// device.UCSR0B |= RXEN0 | TXEN0 | TXCIE0| RXCIE0;
	}

	method sendPackage(packet:TOS_Msg) {
		local data= 36; /* MSG_DATA_SIZE */
		local i=0;
		for (i = 0; i < 36; i++)
			packetbuf[i] = packet.getByte(i)::char;
		while (data > 0) {
			data -=transmit(packetbuf,36-data, data);
		}
	}

	method receive(buf : char[], i : int, l : int) : int {
		local count : int = 0;
		local mask = MCU.disable();
		while (count < l and r_queue.size() > 0) {
			buf[i + count] = r_queue.dequeue();		
			count++;
		}
		MCU.restore(mask);
		return count;
	}

	method rec_handler() {
		device.UCSR0B = RXEN0 | TXEN0 | TXCIE0| RXCIE0;
	}

	method printString(buf: char[], length:int) {
		transmit(buf,0,length);
	}
	method printHex16(data_in :16) {
		// TODO: convert this to use simpler raw operators
		local tem:int;

		if ((data_in & 0x000f)::int < 0xa::int) printBuf[3] = ('0'::int + (data_in & 0x000f)::int)::char;
		else printBuf[3] = ('A'::int + (data_in & 0x000f)::int - 0xA::int)::char;

		tem = ((data_in >> 4) & 0x000f)::int;
		if (tem::char < 0xa::char) printBuf[2] = ('0'::int + (tem))::char;

		else printBuf[2] = ('A'::int + (tem)-0xa::int)::char;

		tem = ((data_in >>8) & 0x000f)::int;

		if (tem::char < 0xa::char) printBuf[1] = ('0'::int + (tem))::char;
		else printBuf[1] = ('A'::int + (tem)-0xa::int)::char;

		tem = ((data_in >>12) & 0x000f)::int;

		if (tem::char < 0xa::char) printBuf[0] = ('0'::int + (tem))::char;
		else printBuf[0] = ('A'::int + (tem)-0xa::int)::char;

		printBuf[4]='\n';
		USART0.transmit(printBuf, 0, 5);
	}

	method printHex8(data_in :8) {
		// TODO: convert this to use simpler raw operators
		local tem: int;
		if ((data_in & 0x0f)::char < 0xa::char) printBuf[1] = ('0'::int + (data_in & 0x0f)::int)::char;
		else printBuf[1] = ('A'::int + (data_in & 0x0f)::int-0xA::int)::char;

		tem = (data_in >> 4)::int;
		if (tem::char < 0xa::char) printBuf[0] = ('0'::int + (tem))::char;
		else printBuf[0] = ('A'::int + (tem) - 0xa::int)::char;

		printBuf[2]='\n';
		USART0.transmit(printBuf, 0, 3);
	}
}

component USART1 {

	//bit names for UCSR0A register
	field RXC1: 8 = 0x80;   // USART receive complete
	field TXC1: 8 = 0x40;   // USART transmit complete
	field UDRE1: 8 = 0x20;  // USART data registe empty
	field FE1: 8 = 0x10;    // Frame error : set to 1 when writing to UCSR0A
	field DOR1: 8 = 0x08;   // Data over run : set to 0 when writing to UCSR0A
	field UPE1: 8 = 0x04;   // Parity error : set to 0 when writing to UCSR0A
	field U2X1: 8 = 0x02;   // Double USART transmission speed : set to 0 in sync mode
	field MPCM1: 8 = 0x01;  // Multi-processor communication mode

	//bit names for UCSR0B register
	field RXCIE1: 8 = 0x80;  // enable receive complete interrupt
	field TXCIE1: 8 = 0x40;  // enable transmission complete interrupt
	field UDRIE1: 8 = 0x20;  // enable data register empty interrupt
	field RXEN1: 8 = 0x10;   // enable receiver
	field TXEN1: 8 = 0x08;   // enable transmitter
	field UCSZ12: 8 = 0x04;  // high end bit of 3 for frame size
	field RXB81: 8 = 0x02;   // 9th bit of received character
	field TXB81: 8 = 0x01;   // 9th bit of transmitted character

	//bit names for UCSR0C register
	field UMSEL1: 8 = 0x40;  // 0 for async, 1 for sync mode
	field UPM11: 8 = 0x20;   // Parity mode
	field UPM10: 8 = 0x10;   // Parity mode
	field USBS1: 8 = 0x08;   // Number of stop bits inserted by transmitter
	field UCSZ11: 8 = 0x04;  // Character size low 2 bits
	field UCSZ10: 8 = 0x02;  // Character size low 2 bits
	field UCPOL1: 8 = 0x01;  // Clock polarity in sync mode

	field r_queue : Queue<char>;  //receive queue
	field t_queue : Queue<char>;  //transmit queue
	
	constructor() {
		r_queue = new Queue<char>(10);
		t_queue = new Queue<char>(10);
	}

	method enable(baud_rate: int, sync_mode : boolean, double_async : boolean) {

		// set character size to 8
		device.UCSR1C = UCSZ11 | UCSZ10;
	
		if (sync_mode) {
			device.UCSR1C = device.UCSR1C | UMSEL1;
		} else if (double_async) {
			device.UCSR1A = U2X1;
		}
	
		// set baud rate
		device.UBRR1H = (baud_rate >> 8) :: 8;
		device.UBRR1L = (baud_rate & 0xFF) :: 8;
 
		// enable interrupts, receiver and transmitter
		device.UCSR1B = RXEN1 | TXEN1 | RXCIE1;
	}

	method disable() {
		// disable receiver, transmitter and reciever
		device.UCSR1B = 0x0;
	}
	method transmit(buf : char[], i : int, l : int) : int {
		local index : int = 0;
		local mask = MCU.disable();
		for (index = 0 ; index < l ; index++) {
			// if (t_queue.enqueue(buf[i + index])) { 
			if (t_queue.size() > 0) {
				// turn on data ready interrupts
				device.UCSR1B = device.UCSR1B | UDRIE1; 
			}
			// } else {
			// MCU.restore(mask);
			// return index;
			// }
		}
		MCU.restore(mask);
		return index;	
	}

	method tran_handler() {
		local c = t_queue.dequeue();
		if (t_queue.size() == 0) {
			//turn off data ready interrupts
			device.UCSR1B = device.UCSR1B & ~UDRIE1;
		}
		device.UDR1 = c;
	}

	method receive(buf : char[], i : int, l : int) : int {
		local count : int = 0;
		local mask = MCU.disable();
		while(count < l and r_queue.size() > 0) {
			buf[i + count] = r_queue.dequeue();		
			count++;
		}
		MCU.restore(mask);
		return count;
	}

	method rec_handler() {
		local c : char = (device.UDR1) :: char;
		r_queue.enqueue(c);
	}
}
