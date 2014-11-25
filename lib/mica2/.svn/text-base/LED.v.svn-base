/**
 * Simple LED driver. Accepts a Pin instance and a color name.
 * Assumes active-low (i.e. low = on).
 * @author Ben L. Titzer
 */
class LED {
	field pin: Pin;
	constructor(p: Pin, color: char[]) {
		pin = p;
	}
	method toggle() {
		pin.out(!pin.in());
	}
	method on() {
		pin.out(false);
	}
	method off() {
		pin.out(true);
	}
}
