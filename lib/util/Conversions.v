component Conversions {

	method int_char(v: int): char { return v :: char; }
	method char_int(v: char): int { return v :: int; }

	method int_raw32(v: int): 32 { return v :: 32; }
	method raw32_int(v: 32): int { return v :: int; }

	method int_raw16(v: int): 16 { return v :: 16; }
	method raw16_int(v: 16): int { return v :: int; }

	method char_raw8(v: char): 8 { return v :: 8; }
	method raw8_char(v: 8): char { return v :: char; }

	method bool_raw1(v: boolean): 1 { return v ? 0b1 : 0b0; }
	method raw1_bool(v: 1): boolean { return v == 0b1; }
}