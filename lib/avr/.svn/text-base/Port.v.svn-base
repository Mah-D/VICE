class Pin {
	field port: Port;
	field mask: 8;

	constructor(p: Port, b: int) {
		port = p;
		mask = 0b00000001 << b;
	}
	method in(): boolean { return (port.in() & mask) != 0; }
	method out(b: boolean) {
		local v = port.in();
		port.out(b ? v | mask : v & ~mask);
	}
}

class Port {
	// -- Abstract methods ------------------------------------
	method out(v: 8);
	method in(): 8;
	method setDirection(dir: 8);
	method getDirection(): 8;

	// -- Helper methods --------------------------------------
	method setOutput() { setDirection(0xff); }
	method setInput() { setDirection(0x00); }
	method enableOutput(dir: 8) { setDirection(getDirection() | dir); }
	method enableInput(dir: 8) { setDirection(getDirection() & ~dir); }
}

class PortA extends Port {
	method out(v: 8) { device.PORTA = v; }
	method in(): 8 { return device.PINA; }
	method setDirection(dir: 8) { device.DDRA = dir; }
	method getDirection(): 8 { return device.DDRA; }
}

class PortB extends Port {
	method out(v: 8) { device.PORTB = v; }
	method in(): 8 { return device.PINB; }
	method setDirection(dir: 8) { device.DDRB = dir; }
	method getDirection(): 8 { return device.DDRB; }
}

class PortC extends Port {
	method out(v: 8) { device.PORTC = v; }
	method in(): 8 { return device.PINC; }
	method setDirection(dir: 8) { device.DDRC = dir; }
	method getDirection(): 8 { return device.DDRC; }
}

class PortD extends Port {
	method out(v: 8) { device.PORTD = v; }
	method in(): 8 { return device.PIND; }
	method setDirection(dir: 8) { device.DDRD = dir; }
	method getDirection(): 8 { return device.DDRD; }
}

class PortE extends Port {
	method out(v: 8) { device.PORTE = v; }
	method in(): 8 { return device.PINE; }
	method setDirection(dir: 8) { device.DDRE = dir; }
	method getDirection(): 8 { return device.DDRE; }
}

component PortUtil {
	method readDDR(i: int): 8 {
		switch ( i ) {
			case (0) return device.DDRA;
			case (1) return device.DDRB;
			case (2) return device.DDRC;
			case (3) return device.DDRD;
			case (4) return device.DDRE;
		}
		return 0x00;
	}

	method writeDDR(i: int, v: 8) {
		switch ( i ) {
			case (0) device.DDRA = v;
			case (1) device.DDRB = v;
			case (2) device.DDRC = v;
			case (3) device.DDRD = v;
			case (4) device.DDRE = v;
		}
	}

	method readPort(i: int): 8 {
		switch ( i ) {
			case (0) return device.PINA;
			case (1) return device.PINB;
			case (2) return device.PINC;
			case (3) return device.PIND;
			case (4) return device.PINE;
		}
		return 0x00;
	}

	method writePort(i: int, v: 8) {
		switch ( i ) {
			case (0) device.PORTA = v;
			case (1) device.PORTB = v;
			case (2) device.PORTC = v;
			case (3) device.PORTD = v;
			case (4) device.PORTE = v;
		}
	}

	method readPin(i: int, b: int): 1 {
		return readPort(i)[b];
	}

	method writePin(i: int, b: int, v: 1) {
		local val = readPort(i);
		val[b] = v;
		writePort(i, val);
	}
}