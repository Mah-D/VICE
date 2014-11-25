/*
 * This program tests the operation of the SPI driver for the AVR.
 * @author Akop Palyan
 */
program TestPM {
	entrypoint main = TestPM.start;
}

component TestPM {
	method start() {
		MCU.enable();
		Power.enable();
		Power.adjustPower();
	}
}
