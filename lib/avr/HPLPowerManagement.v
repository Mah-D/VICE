/**
 * @author Xiaoli Gong
 * @Date Feb 23, 2009
 */
component Power {
	field disableCount: int = 1;
	field powerLevel: 8;

	field IDLE: 8		= 0x00;
	field ADC_NR: 8		= 0x08;
	field POWER_DOWN: 8	= 0x10;
	field POWER_SAVE: 8	= 0x18;
	field STANDBY: 8	= 0x14;
	field EXT_STANDBY: 8	= 0x1c;

	field OCIE0: int = 1;
	field TOIE0: int = 0;

	method getPowerLevel(): 8 {
		local diff: int;
		if ((device.TIMSK & 0xfc) != 0x00) { // Are external timers running?
			return IDLE;
		} else if (device.SPCR[7] == 0b1) { // SPI (Radio stack on mica)
			return IDLE;
		} else if (device.ADCSRA[7] == 0b1) { // ADC is enabled
			return ADC_NR;
		} else if ((device.TIMSK & 0x03) != 0x00) {
			diff = device.OCR0::int - device.TCNT0::int;
			if (diff < 16) return EXT_STANDBY;
			return POWER_SAVE;
		} else {
			return POWER_DOWN;
		}
	}
	
	method doAdjustment() {
		local foo: 8;
		local mcu: 8;
		powerLevel = foo = getPowerLevel();
		mcu = device.MCUCR;
		mcu &= 0xe3;
		if ((foo == EXT_STANDBY) or (foo == POWER_SAVE)) {
			mcu |= IDLE;
			while ((device.ASSR & 0x7) != 0x00) {
				// do nothing.
			}
			mcu &= 0xe3;
		}
		mcu |= foo;
		device.MCUCR = mcu;
		device.MCUCR[5] = 0b1;
	}	

	method adjustPower() {
		local mcu: 8;
		if (disableCount <= 0) doAdjustment();
		else {
			mcu = device.MCUCR;
			mcu &= 0xe3;
			mcu |= IDLE;
			device.MCUCR = mcu;
			device.MCUCR[5] = 0b1;
		}
	}

	method enable() {
		local mask = MCU.disable();
		disableCount--;
		MCU.restore(mask);
	}

	method disable() {
		local mask = MCU.disable();
		disableCount++;
		MCU.restore(mask);
	}
}
