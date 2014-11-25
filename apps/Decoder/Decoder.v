/*
 * The decoder program builds and uses bit pattern recognizers,
 * which are finite state machines that can be used to decode
 * instructions and other binary data. 
 * @author Ben L. Titzer
 */
program Decoder {
    entrypoint main = Decoder.start;
}

component Decoder {
//	field db1: Builder;
//	field db2: Builder;
	field d1: Recognizer = build_D1(new Builder());
//	field d2: Recognizer = build_D2(db2 = new Builder());
	field values: int[] = { 34, 75, 92, 11, 8, 112, 0b0000010100000000 :: int, 0b0000010100001111 :: int };


	method start() {
		local i: int, j: int;
		for (i = 0; i < 1000; i++ ) {
			for ( j = 0; j < values.length; j++ ) {
				d1.decode(values[j]);
			}
		}
	}

	method build_D1(d: Builder): Recognizer {
		d.add(0b0000000000000000, 0b0000100100001111, A);
		d.add(0b0000100000000000, 0b0000100100001111, B);
		d.add(0b0000000100000000, 0b0000010100001111, C);
		d.add(0b0000010100000000, 0b0000010100001111, D);

		d.add(0b00001011, 0b00001111, E);
		d.add(0b00001001, 0b00001111, F);
		d.add(0b00000011, 0b00001111, none);
		d.add(0b00001100, 0b00001111, none);
		d.add(0b11001111, 0b11001111, none);
		d.add(0b01001111, 0b11001111, none);
		d.add(0b00000111, 0b11001111, none);
		d.add(0b10000111, 0b11001111, none);
		d.add(0b00000101, 0b11111111, none);
		d.add(0b00010101, 0b11111111, none);
		d.add(0b00100101, 0b11111111, none);
		d.add(0b00110101, 0b11111111, none);
		d.error(error);

		return d.build();
	}

	method build_D2(d: Builder): Recognizer {
		d.add(0, 0b11111111, none);
		d.add(1, 0b11111111, none);
	//	d.add(2, 0b11111111, none);
	//	d.add(3, 0b11111111, none);
	//	d.add(4, 0b11111111, none);
	//	d.add(5, 0b11111111, none);
	//	d.add(6, 0b11111111, none);
	//	d.add(7, 0b11111111, none);
		d.error(error);
		return d.build();
	}

	method none(v: int) { }
	method error(v: int) { }
	method A(v: int) { }
	method B(v: int) { }
	method C(v: int) { }
	method D(v: int) { }
	method E(v: int) { }
	method F(v: int) { }
	method G(v: int) { }
	method H(v: int) { }
	method I(v: int) { }
	method J(v: int) { }
	method K(v: int) { }
	method L(v: int) { }
	method M(v: int) { }
	method N(v: int) { }
}
