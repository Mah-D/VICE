/*
 * SPI (Serial peripheral interface) driver.
 * @author Akop Palyan
 * @ modified by Xiaoli Gong
 * @ a hook function is needed to catch the interrupt of the SPI
 * @ Dec 31,2008
 * @ add a work mode selection and keep the old machinism work 
 * @ Feb 23, 2009
 */

component SPI {
	// SPI Control Register
	field SPIE: 3 = 07;
	field SPE:  3 = 06;
	field DORD: 3 = 05;
	field MSTR: 3 = 04;
	field CPOL: 3 = 03;
	field CPHA: 3 = 02;
	field SPR:  3 = 01;

	// SPI Status Register
	field SPIF:  3 = 07;
	field WCOL:  3 = 06;
	field SPI2X: 3 = 00;

	field t_queue: Queue<char> = new Queue<char>(10);
	field r_queue: Queue<char> = new Queue<char>(10);

	field transmitting: boolean;
	field master: boolean = false;
	field OutgoingByte:8 = 0x00;

	field callback: function(8) = none;

	field sendperByte:boolean = true;
	field sendbyQueue:boolean = false;
	field workMode: boolean= sendbyQueue;

	method setWorkMode(mode: boolean) {
		workMode = mode;
		if (mode == sendbyQueue) {
			clrCallback();
		}
	}

	method startMaster() {
		transmitting = false;
		master = true;

		device.SPCR[SPE::int] = 0b1;
		device.SPCR[MSTR::int] = 0b1;
		device.SPCR[SPR::int] = 0b1;
		device.SPCR[SPIE::int] = 0b1;
		Mica2.portB.setDirection(0x6);
	 }

	method startSlave() {
		transmitting = false;
		master = false;

		device.DDRB[1] = 0b0; /////spi sck input
		device.DDRB[2] = 0b0; /////mosi input
		device.DDRB[3] = 0B0; /////miso input

		device.SPCR[CPOL::int] = 0b0;
		device.SPCR[CPHA::int] = 0b0;
		device.SPCR[SPIE::int] = 0b1;
		device.SPCR[SPE::int] = 0b1;	
		// Mica2.portB.setDirection(0x8);
	}

	method transmitByte(data:8) {
		OutgoingByte = data;
	}
	method transmit() {
		if (!transmitting) {
			device.SPDR = t_queue.dequeue();
			transmitting = true;
		}
	}

	method interruptHandler() {
		local temp:8;
		temp = device.SPDR::8;
		if (workMode == sendbyQueue) {
			r_queue.enqueue(temp::char);
			if (!t_queue.empty()) {
				device.SPDR = t_queue.dequeue();
			} else if (master) {
				transmitting = false;
			}
		}
		if (workMode == sendperByte) {
			device.SPDR = OutgoingByte;
			if (master) {
				transmitting = false;
			}
			callback(temp);
		}
	}
	method enableIntr() {
		device.SPCR = 0xC0;
		device.DDRB[0] = 0b0;
		Power.adjustPower();
	}

	method disableIntr() {
		device.SPCR[7] = 0b0;
		device.DDRB[0] = 0b1;
		device.PORTB[0] = 0b0;
		Power.adjustPower();
	}

	method rxMode() {
		device.DDRB[2] = 0b0; /////mosi input
		device.DDRB[3] = 0B0; /////miso input
	}
	method txMode() {
		device.DDRB[3] = 0B1; /////miso output
		device.DDRB[2] = 0b1; /////mosi output
	}


	method writeByte(buffer: 8): boolean {
		local nenqueued: int = 0;
		local result = false;
		local mask = MCU.disable();
		if (workMode != sendperByte) return result;
		result = true;
		transmitByte(buffer);
		MCU.restore(mask);
		return result;
	}
	method write(buffer: char[], index: int, nbytes: int): int {
		local nenqueued: int = 0;
		local mask = MCU.disable();
		while (nbytes > 0 and t_queue.enqueue(buffer[index + nenqueued])) {
			nbytes = nbytes - 1;
			nenqueued = nenqueued + 1;
		}
		if (master and !t_queue.empty()) transmit();
		MCU.restore(mask);
		return nenqueued;
	}

	method read(buffer: char[], index: int, nbytes: int): int {
		local ndequeued: int = 0;
		local mask = MCU.disable();
		while (nbytes > 0 and !r_queue.empty()) {
			buffer[index + ndequeued] = r_queue.dequeue();
			nbytes = nbytes - 1;
			ndequeued = ndequeued + 1;
		}
		MCU.restore(mask);
		return ndequeued;
	}

	method none(inputvalue: 8) {
	}

	method setCallback(f: function(8)) {
		callback = f;
	}

	method clrCallback(){
		callback=none;
	}
}
