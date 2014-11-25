 /*  
  * This module provides the layer2 functionality for the mica2 radio.
  * While the internal architecture of this module is not CC1000 specific,
  * It does make some CC1000 specific calls via CC1000Control.
  * @author Xiaoli Gong
  */
component CC1000Radio {

	field CC1K_SquelchInit: int		= 312;
	field CC1K_SquelchTableSize: int	= 9;     
	field CC1K_MaxRSSISamples: int		= 5;
	field CC1K_Settling: int		= 1;
	field CC1K_ValidPrecursor: int		= 2;
	field CC1K_SquelchIntervalFast: int	= 128;
	field CC1K_SquelchIntervalSlow: int	= 2560;
	field CC1K_SquelchCount: int		= 30;
	field CC1K_SquelchBuffer: int		= 0;

	field CC1K_LPL_STATES: int		= 7;

	field CC1K_LPL_PACKET_TIME: int		= 16;
	field TX_STATE: int			= 0;
	field DISABLED_STATE: int		= 1;
	field IDLE_STATE: int			= 2;
	field PRETX_STATE: int			= 3;
	field SYNC_STATE: int			= 4;
	field RX_STATE: int			= 5;
	field SENDING_ACK: int			= 6;
	field  POWER_DOWN_STATE: int		= 7;
	field NULL_STATE: int			= 8;

	field TXSTATE_WAIT: int			= 0;
	field TXSTATE_START: int		= 1;
	field TXSTATE_PREAMBLE: int		= 2;
	field TXSTATE_SYNC: int			= 3;
	field TXSTATE_DATA: int			= 4;
	field TXSTATE_CRC: int			= 5;
	field TXSTATE_FLUSH: int		= 6;
	field TXSTATE_WAIT_FOR_ACK: int		= 7;
	field TXSTATE_READ_ACK: int		= 8;
	field TXSTATE_DONE: int			= 9;

	field SYNC_WORD: 16			= 0x33cc;
	field NSYNC_WORD: 16			= 0xcc33;
	field SYNC_BYTE: 8			= 0x33;
	field NSYNC_BYTE: 8			= 0xcc;
	field ACK_LENGTH: int			= 16;
	field MAX_ACK_WAIT: int			= 18;

	field TOS_LOCAL_ADDRESS: 16		= 0x0001;  
	field TOS_BCAST_ADDR: 16		= 0xffff;

	field CC1K_LPL_PreambleLength: 8[];
	field CC1K_LPL_SleepTime: 8[];
	field CC1K_LPL_SleepPreamble: 8[];

	field ack_code: 8[] = new 8[3]; 
	field RadioState: int;
	field RadioTxState: int;
	field RSSIInitState: int;

	field iRSSIcount: int;
	field iSquelchCount: int;
	field txlength: 16;
	field rxlength: int;

	field txbufptr: TOS_Msg;  // pointer to transmit buffer
	field rxbufptr: TOS_Msg;  // pointer to receive buffer

	field NextTxByte: 8;

	field lplpower: int;		// low power listening mode
	field lplpowertx: int;		// low power listening transmit mode

	field preamblelen: int;		// current length of the preamble

	field PreambleCount: int;	// found a valid preamble
	field SOFCount: int;

	field search_word: 16;

	field RxShiftBuf: 16;
	field RxBitOffset: int;		// bit offset for spibus
	field RxByteCnt: int;		// received byte counter
	field TxByteCnt: int;
	field RSSISampleFreq: int;	// in Bytes rcvd per sample
	field bInvertRxData: boolean;	// data inverted
	field bTxPending: boolean;
	field bTxBusy: boolean;
	field bAckEnable: boolean;
	field usRunningCRC: 16;		// Running CRC variable
	field usRSSIVal: int;		// suppress nesc warnings
	field usSquelchVal: int;
	field usTempSquelch: int;
	field usSquelchIndex: int;

	field usSquelchTable: int[] = new int[CC1K_SquelchTableSize];

	field sMacDelay: int;    // MAC delay for the next transmission
	field LocalAddr: 16;

	field receivecallback: function(TOS_Msg): TOS_Msg;
	field sendDonecallback: function(TOS_Msg, boolean);
	field checksum: CRC;
	field tempArray: int [];
	field firedTickValue: int;

	constructor() {
		checksum = new CRC();

		tempArray = new int[CC1K_SquelchTableSize];

		ack_code[0] = 0xab;
		ack_code[1] = 0xba;
		ack_code[2] = 0x83;
		rxbufptr = new TOS_Msg();

		CC1K_LPL_PreambleLength = new 8[CC1K_LPL_STATES * 2];
		CC1K_LPL_PreambleLength[0] = 0x00;
		CC1K_LPL_PreambleLength[1] = 0x08;
		CC1K_LPL_PreambleLength[2] = 0x00;
		CC1K_LPL_PreambleLength[3] = 94::8;
		CC1K_LPL_PreambleLength[4] = 0x00;
		CC1K_LPL_PreambleLength[5] = 250::8;
		CC1K_LPL_PreambleLength[6] = 0x01;
		CC1K_LPL_PreambleLength[7] = 0x73;
		CC1K_LPL_PreambleLength[8] = 0x01;
		CC1K_LPL_PreambleLength[9] = 0xea;
		CC1K_LPL_PreambleLength[10] = 0x04;
		CC1K_LPL_PreambleLength[11] = 0xbc;
		CC1K_LPL_PreambleLength[12] = 0x0a;
		CC1K_LPL_PreambleLength[13] = 0x5e;

		CC1K_LPL_SleepTime = new 8[CC1K_LPL_STATES * 2];
		CC1K_LPL_SleepTime[0] = 0x00;
		CC1K_LPL_SleepTime[1] = 0x00;
		CC1K_LPL_SleepTime[2] = 0x00;
		CC1K_LPL_SleepTime[3] = 20::8;
		CC1K_LPL_SleepTime[4] = 0x00;
		CC1K_LPL_SleepTime[5] = 85::8;
		CC1K_LPL_SleepTime[6] = 0x00;
		CC1K_LPL_SleepTime[7] = 135::8;
		CC1K_LPL_SleepTime[8] = 0x00;
		CC1K_LPL_SleepTime[9] = 185::8;
		CC1K_LPL_SleepTime[10] = 0x01;
		CC1K_LPL_SleepTime[11] = 0xe5;
		CC1K_LPL_SleepTime[12] = 0x04;
		CC1K_LPL_SleepTime[13] = 0x3d;	

		CC1K_LPL_SleepPreamble = new 8[CC1K_LPL_STATES];
		CC1K_LPL_SleepPreamble[0] = 0x00;
		CC1K_LPL_SleepPreamble[1] = 0x08;
		CC1K_LPL_SleepPreamble[2] = 0x08;
		CC1K_LPL_SleepPreamble[3] = 0x08;
		CC1K_LPL_SleepPreamble[4] = 0x08;
		CC1K_LPL_SleepPreamble[5] = 0x08;
		CC1K_LPL_SleepPreamble[6] = 0x08;

		receivecallback = dummyReceiveCallback;
		sendDonecallback = dummysendDoneCallback;
	}

	method PRG_RDB(value: 8): int{
		return (value::int);
	}

	method dummyReceiveCallback(data: TOS_Msg):TOS_Msg {
		return data;
	}

	method dummysendDoneCallback(data: TOS_Msg, result: boolean) {
	}

	method SetReceiveCallback(callback: function(TOS_Msg): TOS_Msg) {
		receivecallback = callback;
	}

	method SetsendDoneCallback(callback: function(TOS_Msg, boolean)) {
		sendDonecallback = callback;
	}

	method SquelchTimerstart(interval : int) {
		local ticks: int;
		device.ASSR = 0b1000;               // set Timer0 to asynch operation
		// Timer0.setMode(Timer0.MODE_CTC);    // use clear timer on compare mode
		Timer0.setMode(Timer0.MODE_NORMAL);    // all the timer used in this system is ONE_SHOT timer

		ticks = Timer0.getCount() + interval;
		if ((ticks < firedTickValue) or (firedTickValue <0) ) {
			Timer0.setCompareMatch(ticks);         // compare matches every (16 * 1024) cycles
			firedTickValue = ticks;
		}
		device.TIMSK = 0b10;                // restore the Timer0 compare interrupt

		// Timer0.setDivider(Timer0.DIV_1024); // set divider to 1024
		Timer0.setDivider(Timer0.DIV_1); // set divider to 1

		Mica2.setTimerCompare(SquelchTimerfired);
		// Timer0.enable();
		// Mica2.startTimer();
	}

	method SquelchTimerstop() {
		Timer0.disable();
	}

	method WakeupTimerstart(interval: int) {
		// TODO
	}

	method WakeupTimerstop() {
		// TODO: Timer0.disable();
	}

	method adjustSquelch() {
		local i: int, j: int, min: int;
		local min_value: int = 0;
		local tempsquelch: 32;

		local mask: 8;

		mask = MCU.disable();
		usSquelchTable[usSquelchIndex] = usTempSquelch;
		usSquelchIndex++;
		if (usSquelchIndex >= CC1K_SquelchTableSize) usSquelchIndex = 0;
		if (iSquelchCount <= CC1K_SquelchCount) iSquelchCount++;  
		MCU.restore(mask);

		for (i = 0; i < CC1K_SquelchTableSize; i++) {
			tempArray[i] = usSquelchTable[i];
		}

		min = 0;
		for (j = 0; j < 3; j++) {
			for (i = 1; i < CC1K_SquelchTableSize; i++) {
				if ((tempArray[i] != 65535) and 
					(((tempArray[i]) > (tempArray[min])) or
						(tempArray[min] == 65535))) {
					min = i;
				}
			}
			min_value = tempArray[min];
			tempArray[min] = 0xFFFF::int;
		}

		tempsquelch = ((usSquelchVal *32) + (min_value *2))::32;
		// atomic usSquelchVal = (16)((tempsquelch / 34) & 0x0FFFF);
		mask = MCU.disable();
		usSquelchVal = ((tempsquelch::int / 34) & 0xFFFF::int)::int;
		MCU.restore(mask);
	}

	method PacketRcvd(): boolean {
		local pBuf: TOS_Msg;
		local mask: 8;

		mask = MCU.disable();
		rxbufptr.time = 0::16;
		pBuf = rxbufptr;
		MCU.restore(mask);
		pBuf = receivecallback(pBuf);
		mask = MCU.disable();
		rxbufptr = pBuf;
		rxbufptr.length = 0x00;
		MCU.restore(mask);
		SPI.enableIntr();
		return true;
	}

	method PacketSent() {
		local pBuf:TOS_Msg; // store buf on stack 
		local mask: 8;
		mask = MCU.disable();
		txbufptr.time = 0::16;
		pBuf = txbufptr;
		bTxBusy = false;
		SPI.writeByte(0xaa); // reset the buffer in device driver
		MCU.restore(mask);
		sendDonecallback(pBuf,true);
	}

	method init(): boolean {
		local i: int;
		local mask: 8;
		mask = MCU.disable();
		RadioState = DISABLED_STATE;
		RadioTxState = 2 /*TXSTATE_PREAMBLE*/;
		RSSIInitState = NULL_STATE;
		rxbufptr.length = 0x00;
		// MSG_DATA_SIZE is 36 by default
		rxlength = (36 - 2);
		RxBitOffset = 0;
		iSquelchCount = 0;

		PreambleCount = 0;
		RSSISampleFreq = 0;
		// RxShiftBuf.W = 0;
		RxShiftBuf = 0::16;
		iRSSIcount = 0;
		bTxPending = false;
		bTxBusy = false;
		bAckEnable = false;
		sMacDelay = -1;
		usRSSIVal = -1;
		usSquelchIndex = 0;
		lplpower = lplpowertx = 0;
		usSquelchVal = CC1K_SquelchInit;
		MCU.restore(mask);

		for (i = 0; i < CC1K_SquelchTableSize; i++)
			usSquelchTable[i] = CC1K_SquelchInit;

		SPI.writeByte(0xaa); // preamble byte as initial value
		SPI.setCallback(SpidataReady);
		SPI.setWorkMode(SPI.sendperByte);
		SPI.startSlave(); // set spi bus to slave mode

		CC1000Control.init();
		CC1000Control.SelectLock(0x09);		// Select MANCHESTER VIOLATION
		bInvertRxData = CC1000Control.GetLOStatus();

		ADC.setConvFunc(ADCdataReady);
		ADC.enable();

		LocalAddr = TOS_LOCAL_ADDRESS;

		MCU.enable();
		firedTickValue = -1;

		return true;
	}

	method EnableRSSI(): boolean {
		return true;
	}

	method DisableRSSI(): boolean {
		return true;
	}

	method GetTransmitMode(): 8 {
		return lplpowertx::8;
	}

	/**
	 * Set the state of low power transmit on the chipcon radio.
	 * The transmit mode of the sender *must* match the receiver in
	 * order for the receiver to successfully get the packet.
	 * <p>
	 * The default power up state is 0 (radio always on).
	 * See CC1000Const.h for low power duty cycles and bandwidth
	 */
	method SetTransmitMode(power: 8): boolean {
		local mask: 8;

		if ((power::int >= CC1K_LPL_STATES) or (power == lplpowertx)) return false;

		// check if the radio is currently doing something
		if ((!bTxPending) and	((RadioState ==	7 /*POWER_DOWN_STATE*/) or 
			(RadioState == 2 /*IDLE_STATE*/ ) or
			(RadioState == DISABLED_STATE))) {
			mask = MCU.disable();
			lplpowertx = power::int;
			preamblelen = PRG_RDB(CC1K_LPL_PreambleLength[(lplpowertx*2)]);
			preamblelen = preamblelen * 256 + PRG_RDB(CC1K_LPL_PreambleLength[((lplpowertx * 2) + 1)]);
			MCU.restore(mask);
			return true;
		}
		return true;
	}

	/**
	 * Set the state of low power listening on the chipcon radio.
	 * <p>
	 * The default power up state is 0 (radio always on).
	 * See CC1000Const.h for low power duty cycles and bandwidth
	 */
	method SetListeningMode(power: 8): boolean {
		local mask: 8;
		// valid low power listening values are 0 to 3
		// 0 is "always on" and 3 is lowest duty cycle
		// 1 and 2 are in the middle
		if ((power::int >= CC1K_LPL_STATES) or (power == lplpower)) return false;

		// check if the radio is currently doing something
		if ((!bTxPending) and ((RadioState ==	7 /*POWER_DOWN_STATE*/) or 
			(RadioState == 2 /*IDLE_STATE*/ ) or
			(RadioState == DISABLED_STATE))) {

			// change receiving function in CC1000Radio
			// WakeupTimer.stop();
			WakeupTimerstop();
			mask = MCU.disable();
			if (lplpower == lplpowertx) {
				lplpowertx = power::int;
			}
			lplpower = power::int;
			MCU.restore(mask);

			// if successful, change power here
			if (RadioState == 2 /*IDLE_STATE*/) {
				RadioState = DISABLED_STATE;
				stop();
				start();
			}
			if (RadioState == 7 /*POWER_DOWN_STATE*/) {
				RadioState = DISABLED_STATE;
				start();
			}
		} else {
			return false;
		}
		return true;
	}

	method GetListeningMode(): 8 {
		return lplpower::8;
	} 


	method SquelchTimerfired() {
		local currentRadioState: int = RadioState;
		if (currentRadioState == 1 /*DISABLED_STATE*/) {
			RSSIInitState = currentRadioState;
			ADC.startConv(0, true);
		}
	}

	method WakeupTimerfired() {
		local sleeptime: int;
		local tempTxPending: boolean;
		local currentRadioState: int;

		local mask: 8;

		if (lplpower == 0) return;

		mask = MCU.disable();
		currentRadioState = RadioState;
		tempTxPending = bTxPending;
		MCU.restore(mask);

		switch (currentRadioState) {
			case (2 /*IDLE_STATE*/) {
				sleeptime = PRG_RDB(CC1K_LPL_SleepTime[lplpower*2]) ;
				sleeptime = sleeptime * 256 + PRG_RDB(CC1K_LPL_SleepTime[(lplpower*2)+1]);
				if (!tempTxPending) {
					RadioState = 7 /*POWER_DOWN_STATE*/;
					WakeupTimerstart(sleeptime);
					SquelchTimerstop();
					CC1000Control.stop();
					SPI.disableIntr();
				} else {
					WakeupTimerstart(CC1K_LPL_PACKET_TIME*2);
				}
			}
			case (7 /*POWER_DOWN_STATE*/) {
				sleeptime = PRG_RDB(CC1K_LPL_SleepPreamble[lplpower]);
				RadioState = 2 /*IDLE_STATE*/;
				CC1000Control.start();
				CC1000Control.BIASOn();
				SPI.rxMode();		// SPI to miso
				CC1000Control.RxMode();
				SPI.enableIntr(); // enable spi interrupt

				if (iSquelchCount > CC1K_SquelchCount) SquelchTimerstart(CC1K_SquelchIntervalSlow);
				else SquelchTimerstart(CC1K_SquelchIntervalFast);

				WakeupTimerstart(sleeptime);
			}
			default {
				WakeupTimerstart(CC1K_LPL_PACKET_TIME * 2);
			}
		}
		return;
	}

	method stop(): boolean {
		RadioState = DISABLED_STATE;
		SquelchTimerstop();
		WakeupTimerstop();
		ADC.disable();
		CC1000Control.stop();
		SPI.disableIntr(); // disable spi interrupt
		return true;
	}

	method start(): boolean {
		local mask: 8;
		local currentRadioState: int;
		currentRadioState = RadioState;
		ADC.setConvFunc(ADCdataReady);
		if (currentRadioState == DISABLED_STATE) {
			mask = MCU.disable();
			rxbufptr.length = 0x00;
			RadioState = 2 /*IDLE_STATE*/;
			bTxPending = bTxBusy = false;
			sMacDelay = -1;
			preamblelen = PRG_RDB(CC1K_LPL_PreambleLength[lplpowertx * 2]) * 256;
			preamblelen += PRG_RDB(CC1K_LPL_PreambleLength[(lplpowertx * 2) + 1]);
			MCU.restore(mask);
			if (lplpower == 0) {
				// all power on, captain!
				rxbufptr.length = 0x00;
				RadioState = 2 /*IDLE_STATE*/;
				CC1000Control.start();
				CC1000Control.BIASOn();
				SPI.rxMode();		// SPI to miso
				CC1000Control.RxMode();

				if (iSquelchCount > CC1K_SquelchCount) SquelchTimerstart(CC1K_SquelchIntervalSlow);
				else SquelchTimerstart(CC1K_SquelchIntervalFast);

				SPI.enableIntr(); // enable spi interrupt
			} else {
				local sleeptime: int;
				sleeptime = PRG_RDB(CC1K_LPL_SleepTime[lplpower*2]) *256;
				sleeptime = PRG_RDB(CC1K_LPL_SleepTime[(lplpower*2)+1]);
				RadioState = 7 /*POWER_DOWN_STATE*/;

				SquelchTimerstop();

				WakeupTimerstart(sleeptime);
			}
		}
		return true;
	}

	method send(pMsg:TOS_Msg): boolean {
		local mask: 8;
		local Result: boolean = true;
		local currentRadioState: int = 0;

		mask = MCU.disable();
		if (bTxBusy) {
			Result = false;
		} else {
			bTxBusy = true;
			txbufptr = pMsg;
			txbufptr.ack = 0x00;
			txlength = (pMsg.length::int + (36 /*MSG_DATA_SIZE*/ - 29 /*DATA_LENGTH*/ - 2))::16; 
			// initially back off [1,32] bytes (approx 2/3 packet)
			bTxPending = true;
		}
		currentRadioState = RadioState;
		MCU.restore(mask);

		if (Result) {
			// if we're off, start the radio
			if (currentRadioState == 7 /*POWER_DOWN_STATE*/) {
				// disable wakeup timer
				WakeupTimerstop();
				CC1000Control.start();
				CC1000Control.BIASOn();
				CC1000Control.RxMode();
				SPI.rxMode();		// SPI to miso
				SPI.enableIntr(); // enable spi interrupt
				if (iSquelchCount > CC1K_SquelchCount) SquelchTimerstart(CC1K_SquelchIntervalSlow);
				else SquelchTimerstart(CC1K_SquelchIntervalFast);
				WakeupTimerstart(CC1K_LPL_PACKET_TIME*2);
				RadioState = 2 /*IDLE_STATE*/;
			}
		}
		return Result;
	}

	/**********************************************************
	 * make a spibus interrupt handler
	 * needs to handle interrupts for transmit delay
	 * and then go into byte transmit mode with
	 * timer1 baudrate delay as interrupt handler
	 * else
	 * needs to handle interrupts for byte read and detect preamble
	 * then handle reading a packet
	 * PB - We can use this interrupt handler as a transmit scheduler
	 * because the CC1000 continuously clocks in data, regarless
	 * of whether it's good or not.  Thus, this routine will be called
	 * on every 8 ticks of DCLK. 
	 **********************************************************/
	method SpidataReady(data_in: 8) {
		if (bInvertRxData) data_in = ~data_in;

		switch (RadioState) {
			case (0 /*TX_STATE*/) {
				SPI.writeByte(NextTxByte);
				TxByteCnt++;
				switch (RadioTxState) {
					case (2 /*TXSTATE_PREAMBLE*/) {
						if (!(TxByteCnt < preamblelen)) {
							NextTxByte = SYNC_BYTE;
							RadioTxState = 3 /*TXSTATE_SYNC*/;
						}
					}
					case (3 /*TXSTATE_SYNC*/) {
						NextTxByte = NSYNC_BYTE;
						RadioTxState = 4 /*TXSTATE_DATA*/;
						TxByteCnt = -1;
					}
					case (4 /*TXSTATE_DATA*/) {
						if ((TxByteCnt) < txlength::int) {
							NextTxByte = txbufptr.getByte(TxByteCnt);
							usRunningCRC = checksum.crcByte(usRunningCRC,NextTxByte);
						} else {
							NextTxByte = (usRunningCRC)::8;
							RadioTxState =	5 /*TXSTATE_CRC*/;
						}
					}
					case (5 /*TXSTATE_CRC*/) {
						NextTxByte = (usRunningCRC >> 8)::8;
						RadioTxState = TXSTATE_FLUSH;
						TxByteCnt = 0;
					}
					case (6 /*TXSTATE_FLUSH*/) {
						if (TxByteCnt > 3) {
							if ((bAckEnable) and (txbufptr.addr != TOS_BCAST_ADDR)) {
								TxByteCnt = 0;
								RadioTxState = TXSTATE_WAIT_FOR_ACK;
							} else {
								RadioTxState = TXSTATE_DONE;
							}
						}
					}
					case (7 /*TXSTATE_WAIT_FOR_ACK*/) {
						if (TxByteCnt == 1) {
							SPI.rxMode();
							CC1000Control.RxMode();
						}
						if (TxByteCnt > 3) {
							RadioTxState = TXSTATE_READ_ACK;
							TxByteCnt = 0;
							search_word = 0x0000;
						}
					}
					case (8 /*TXSTATE_READ_ACK*/) {
						local i: int;
						for (i = 0; i < 8; i++) {
							search_word <<= 1;
							if (data_in[7] == 0b1) search_word |= 0x01;
							data_in <<= 1;
							if (search_word == 0xba83) {
								txbufptr.ack = 0x01;
								RadioTxState = TXSTATE_DONE;
								return;
							}
						}
					}
				}
				if (TxByteCnt == MAX_ACK_WAIT) {
					txbufptr.ack = 0x00;
					RadioTxState = TXSTATE_DONE;
				}
			}
			case (9 /*TXSTATE_DONE*/) {
				bTxPending = false;
				PacketSent();
				SPI.rxMode();
				CC1000Control.RxMode();
				RadioState = 2 /*IDLE_STATE*/;
				RSSIInitState = RadioState;
				ADC.startConv(0, true);
			}
			default {
				bTxPending = false;
				PacketSent();
				// If the post operation succeeds, goto Idle
				// otherwise, we'll try again.
				SPI.rxMode();
				CC1000Control.RxMode();
				RadioState = 2 /*IDLE_STATE*/;
				RSSIInitState = RadioState;
				// ADC.getData();
				ADC.startConv(0, true);
			}
			case (1 /*DISABLED_STATE*/) {
				// do nothing.
			}
			case (2 /*IDLE_STATE*/ ) { 
				if (((data_in == (0xaa)) or (data_in == (0x55)))) {
					PreambleCount++;
					if (PreambleCount > CC1K_ValidPrecursor) {
						PreambleCount = SOFCount = 0;
						RxBitOffset = RxByteCnt = 0;
						usRunningCRC = 0x0000;
						rxlength = (36 /*MSG_DATA_SIZE*/-2);
						RadioState = SYNC_STATE;
					}
				} else if (bTxPending ) {
					RadioState = 3 /*PRETX_STATE*/;
					RSSIInitState = 3 /*PRETX_STATE*/;
					iRSSIcount = 0;
					PreambleCount = 0;
					// ADC.getData();
					ADC.startConv(0, true);
				}
			}
			case (3 /*PRETX_STATE*/) {
				if (((data_in == (0xaa)) or (data_in == (0x55)))) {
					// Back to the penalty box.
					RadioState = 2 /*IDLE_STATE*/;
				}		
			}
			case (4 /*SYNC_STATE*/) {
				// draw in the preamble bytes and look for a sync byte
				// save the data in a 16 with last byte received as msbyte
				// and current byte received as the lsbyte.
				// use a bit shift compare to find the byte boundary for the sync byte
				// retain the shift value and use it to collect all of the packet data
				// check for data inversion, and restore proper polarity 
				// XXX-PB: Don't do this.
				local i: int;

				if ((data_in == 0xaa) or (data_in == 0x55)) {
					// It is actually possible to have the LAST BIT of the incoming
					// data be part of the Sync Byte.	SO, we need to store that
					// However, the next byte should definitely not have this pattern.
					// XXX-PB: Do we need to check for excessive preamble?
					// RxShiftBuf.MSB = data_in;
					RxShiftBuf = RxShiftBuf & 0x00ff;
					RxShiftBuf = RxShiftBuf | ((data_in::16) << 8);
				} else {
					// TODO: Modify to be tolerant of bad bits in the preamble...
					local usTmp: 16;
					switch (SOFCount) {
						case (0) {
							// RxShiftBuf.LSB = data_in;
							RxShiftBuf = RxShiftBuf & (0xff00::16);
							RxShiftBuf = RxShiftBuf |((data_in::16)&(0x00ff::16));
						}
						case (1) {
							// bit shift the data in with previous sample to find sync
							usTmp = RxShiftBuf;
							RxShiftBuf <<= 8;
							RxShiftBuf |= (data_in::16) & (0x00ff::16);
							for (i = 0; i < 8; i++) {
								usTmp <<= 1;
								if (data_in[7] == 0b1) usTmp |= 0x01;
								data_in <<= 1;
								// check for sync bytes
								if (usTmp == SYNC_WORD) {
									if (rxbufptr.length != 0) {
										RadioState = 2 /*IDLE_STATE*/;
									} else {
										RadioState = RX_STATE;
										RSSIInitState = RX_STATE;
										ADC.startConv(0, true);
										RxBitOffset = 7 - i;
									}
									break;
								}
							}
						}
						case (2) { 
							// bit shift the data in with previous sample to find sync
							usTmp = RxShiftBuf;
							RxShiftBuf <<= 8;
							RxShiftBuf |= (data_in::16) & (0x00ff::16);
							for (i = 0; i < 8; i++) {
								usTmp <<= 1;
								if (data_in[7] == 0b1) usTmp |= 0x01;
								data_in <<= 1;
								// check for sync bytes
								if (usTmp == SYNC_WORD) {
									if (rxbufptr.length !=0) {
										// Leds.redToggle();
										RadioState = 2 /*IDLE_STATE*/;
									} else {
										RadioState = RX_STATE;
										RSSIInitState = RX_STATE;
										// ADC.getData();
										ADC.startConv(0, true);
										RxBitOffset = 7 - i;
									}
									break;
								}
							}
						}
						default {
							// We didn't find it after a reasonable number of tries, so....
							RadioState = 2 /*IDLE_STATE*/;	// Ensures we wait till the end of the transmission
						}
					}
					SOFCount++;
				}
			}
			case (5/*RX_STATE*/) {
				// collect the data and shift into double buffer
				// shift out data by correct offset
				// invert the data if necessary
				// stop after the correct packet length is read
				// return notification to upper levels
				// go back to idle state
				local Byte: 8;

				RxShiftBuf <<= 8;
				RxShiftBuf |= data_in;

				Byte = ((RxShiftBuf >> RxBitOffset) & 0x00ff)::8;

				rxbufptr.setByte(RxByteCnt, Byte);
				RxByteCnt++;

				if (RxByteCnt < rxlength) {
					usRunningCRC = checksum.crcByte(usRunningCRC,Byte);
					if (RxByteCnt == 5) {
						rxlength = (rxbufptr.length)::int;
						if (rxlength > 29 /*TOSH_DATA_LENGTH*/) {
							// The packet's screwed up, so just dump it
							rxbufptr.length = 0x00;
							RadioState = 2 /*IDLE_STATE*/;	// Waits till end of transmission
							return;
						}
						// Add in the header size
						rxlength += 5;
						if (rxbufptr.length == 0) {
							RxByteCnt = 5 + 29;
						}
					}
				} else if (RxByteCnt == rxlength) {
					// usRunningCRC = crcByte(usRunningCRC,Byte);
					usRunningCRC = checksum.crcByte(usRunningCRC,Byte);
					// Shift index ahead to the crc field.
					RxByteCnt = 5 + 29;
				} else if (RxByteCnt >= 36 /*MSG_DATA_SIZE*/) { 
					// Packet filtering based on bad CRC's is done at higher layers.
					// So sayeth the TOS weenies.
					if (rxbufptr.crc == usRunningCRC) {
						rxbufptr.crc = 0x0001;
						if (bAckEnable) {
							if (rxbufptr.addr == TOS_LOCAL_ADDRESS) {
								RadioState = SENDING_ACK; 
								CC1000Control.TxMode();
								SPI.txMode();
								SPI.writeByte(0xaa);
								RxByteCnt = 0;
								return; 
							}
						}
					}
				} else {
					rxbufptr.crc = 0::16;
				}

				SPI.disableIntr();
	
				RadioState = 2 /*IDLE_STATE*/;
				rxbufptr.strength = usRSSIVal::16;
				if (!(PacketRcvd())) {
					// If there are insufficient resources to process the incoming packet
					// we drop it
					rxbufptr.length = 0x00;
					RadioState = 2 /*IDLE_STATE*/;
					SPI.enableIntr();
				}
			}
			case (6 /*SENDING_ACK*/) {
				RxByteCnt++;
				if (RxByteCnt >= ACK_LENGTH) { 
					CC1000Control.RxMode();
					SPI.rxMode();
					SPI.disableIntr();
					RadioState = 2 /*IDLE_STATE*/; //DISABLED_STATE;
					rxbufptr.strength = usRSSIVal::16;
					if (!PacketRcvd()) {
						rxbufptr.length = 0x00;
						RadioState = 2 /*IDLE_STATE*/;
						SPI.enableIntr();
					}
				} else if (RxByteCnt >= ACK_LENGTH - 3 /* sizeof(ack_code)*/ - 2) {
					SPI.writeByte(ack_code[RxByteCnt + 3/* sizeof(ack_code)*/ + 2- ACK_LENGTH]);
				}
			}
		}
	}

	method ADCdataReady(data_in: 10) {
		local currentRadioState: int;
		local initRSSIState: int;
		local data: int;

		local mask: 8;
		mask = MCU.disable();

		data = ((data_in::8) & 0x7f)::int;
		data += ((data_in >> 7))::int * 128;

		currentRadioState = RadioState; 
		initRSSIState = RSSIInitState;
		MCU.restore(mask);
		// find the maximum RSSI value over CC1K_MAX_RSSI_SAMPLES
		switch (currentRadioState) {
			case (2 /*IDLE_STATE*/ ) {
				if (initRSSIState == 2 /*IDLE_STATE*/ ) {
					mask = MCU.disable();
					usTempSquelch = data;
					MCU.restore(mask);
					adjustSquelch();
				}
				mask = MCU.disable();
				RSSIInitState = NULL_STATE;
				MCU.restore(mask);
			}
			case (5 /*RX_STATE*/) {
				if (initRSSIState == RX_STATE) {
					mask = MCU.disable();
					usRSSIVal = data;
					MCU.restore(mask);
				}
				mask = MCU.disable();
				RSSIInitState = NULL_STATE;
				MCU.restore(mask);
			}
			case (3 /*PRETX_STATE*/) {
				local data2: int;
				iRSSIcount++;
				data2 = (usSquelchVal + CC1K_SquelchBuffer + 1);
	
				// if the channel is clear, GO GO GO!
				if ((data2 > (usSquelchVal + CC1K_SquelchBuffer)) and (initRSSIState == 3 /*PRETX_STATE*/)) { 
					SPI.writeByte(0xaa);
					CC1000Control.TxMode();
					SPI.txMode();
					mask = MCU.disable();
					usRSSIVal = data2;
					iRSSIcount = CC1K_MaxRSSISamples;
					TxByteCnt = 0;
					usRunningCRC = 0::16;
					RadioState = 0 /*TX_STATE*/;
					RadioTxState = 2 /*TXSTATE_PREAMBLE*/;
					NextTxByte = 0xaa;
					RSSIInitState = NULL_STATE;
					MCU.restore(mask);
					// Mica2.yellow.toggle();
					return;
				}

				mask = MCU.disable();
				RSSIInitState = NULL_STATE;
				MCU.restore(mask);
				if (iRSSIcount == CC1K_MaxRSSISamples) {
					mask = MCU.disable();
					RadioState = 2 /*IDLE_STATE*/;
					MCU.restore(mask);
				} else {
					mask = MCU.disable();
					RSSIInitState = currentRadioState;
					MCU.restore(mask);
					// ADC.getData();
					ADC.startConv(0, true);
				}
			}
		}
	}

	method GetSquelch(): 16 {
		return usSquelchVal::16;
	}
}
