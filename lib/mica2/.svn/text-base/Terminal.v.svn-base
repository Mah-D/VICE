component Terminal {

	private field buffer: char[] = new char[12];

	method printInt(i: int) {
		local neg: boolean = false;
		local ind: int = 10;

		if (i == 0) {
			printChar('0');
			return;
		}

		if (i < 0) {
			i = -i;
			neg = true;
		}

		while (i > 0) {
			buffer[ind--] = ((i % 10) + '0') :: char;
			i = i / 10;
		}

		if (neg) buffer[ind--] = '-';

		printBuffer(buffer, ind + 1, buffer.length - ind - 1);
	   }

	method printHex(raw: 8) {
		buffer[1] = toHexChar((raw & 0xF) :: int);
		buffer[0] = toHexChar((raw >> 4) :: int);
		printString(buffer, 2);
	}

	method toHexChar(digit: int): char {
		if ( digit < 10 ) return (digit + '0') :: char;
		else return (digit + 'A') :: char;
	}

	method printChar(c: char) {
		buffer[0] = c;
		printString(buffer, 1);
	}

	method printString(string: char[], len: int) {
		printBuffer(string, 0, len);
	}

	method printBuffer(string: char[], ind: int, len: int) {
		MCU.sleepTransfer(string, ind, len, USART0.transmit);
//		local max = ind + len;
//		while (ind < max) {
//			ind = ind + USART0.transmit(string, ind, max - ind);
//		}
	}

	method nextLine() {
		buffer[0] = '\n';
		printString(buffer, 1);
	}
}

