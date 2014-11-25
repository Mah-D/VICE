/*
 * CC1000 hardware direct control interface driver.
 * @author Xiaoli Gong
 */
component HPLChipcon {

	field CC_CHP_OFFSET: int = 6;
	field CC_PALE_OFFSET: int = 4;
	field CC_PCLK_OFFSET: int = 6;
	field CC_PDATA_OFFSET: int = 7; 

	method TOSH_MAKE_CC_CHP_OUT_INPUT() {
		device.DDRA[CC_CHP_OFFSET] = 0b0;
	}
	method TOSH_MAKE_CC_PALE_OUTPUT(){
		device.DDRD[CC_PALE_OFFSET] = 0b1;
	}
	method TOSH_MAKE_CC_PCLK_INPUT(){
		device.DDRD[CC_PCLK_OFFSET] = 0b0;
	}
	method TOSH_MAKE_CC_PCLK_OUTPUT(){
		device.DDRD[CC_PCLK_OFFSET] = 0b1;
	}
	method TOSH_MAKE_CC_PDATA_INPUT(){
		device.DDRD[CC_PDATA_OFFSET] = 0b0;
	}
	method TOSH_MAKE_CC_PDATA_OUTPUT(){
		device.DDRD[CC_PDATA_OFFSET] = 0b1;
	}
	method TOSH_SET_CC_PALE_PIN(){
		device.PORTD[CC_PALE_OFFSET] = 0b1;
	}
	method TOSH_SET_CC_PDATA_PIN(){
		device.PORTD[CC_PDATA_OFFSET] = 0b1;
	}
	method TOSH_SET_CC_PCLK_PIN(){
		device.PORTD[CC_PCLK_OFFSET] = 0b1;
	}
	method TOSH_CLR_CC_PALE_PIN(){
		device.PORTD[CC_PALE_OFFSET] = 0b0;
	}
	method TOSH_CLR_CC_PDATA_PIN(){
		device.PORTD[CC_PDATA_OFFSET] = 0b0;
	}
	method TOSH_CLR_CC_PCLK_PIN(){
		device.PORTD[CC_PCLK_OFFSET] = 0b0;
	}

	method TOSH_READ_CC_PDATA_PIN(): int{
		if (device.PIND[CC_PDATA_OFFSET] == 0b0) return 0;
		else return 1;
	}
	method TOSH_READ_CC_CHP_OUT_PIN(): int{
		if (device.PIND[CC_CHP_OFFSET] == 0b0) return 0;
		else return 1;
	}

	////////////////////////////////////////////////////////////

	method init() {
		TOSH_MAKE_CC_CHP_OUT_INPUT();
		TOSH_MAKE_CC_PALE_OUTPUT();
		TOSH_MAKE_CC_PCLK_OUTPUT();
		TOSH_MAKE_CC_PDATA_OUTPUT();
		TOSH_SET_CC_PALE_PIN();
		TOSH_SET_CC_PDATA_PIN();
		TOSH_SET_CC_PCLK_PIN();
	 }

  //********************************************************/
  // function: write					   */
  // description: accepts a 7 bit address and 8 bit data,  */
  //	creates an array of ones and zeros for each, and   */
  //	uses a loop counting thru the arrays to get	   */
  //	consistent timing for the chipcon radio control	   */
  //	interface.  PALE active low, followed by 7 bits    */
  //	msb first of address, then lsb high for write	   */
  //	cycle, followed by 8 bits of data msb first.  data */
  //	is clocked out on the falling edge of PCLK.	   */
  // Input:  7 bit address, 8 bit data			   */
  //********************************************************/

	method write(addr: 8, data: 8): boolean {
		// address cycle starts here
		local cnt:int;

		addr <<= 1;
		TOSH_CLR_CC_PALE_PIN();  // enable PALE
		for (cnt = 0; cnt < 7; cnt++) {  // send addr PDATA msb first
			if (addr[7] == 0b1) TOSH_SET_CC_PDATA_PIN();
			else TOSH_CLR_CC_PDATA_PIN();
			TOSH_CLR_CC_PCLK_PIN();   // toggle the PCLK
			TOSH_SET_CC_PCLK_PIN();
			addr <<= 1;
		}
	 	TOSH_SET_CC_PDATA_PIN();
		TOSH_CLR_CC_PCLK_PIN();   // toggle the PCLK
		TOSH_SET_CC_PCLK_PIN();

		TOSH_SET_CC_PALE_PIN();  // disable PALE
		// data cycle starts here
		for (cnt = 0; cnt < 8; cnt++) {  // send data PDATA msb first
			if (data[7] == 0b1) TOSH_SET_CC_PDATA_PIN();
			else TOSH_CLR_CC_PDATA_PIN();
			TOSH_CLR_CC_PCLK_PIN();   // toggle the PCLK
			TOSH_SET_CC_PCLK_PIN();
			data <<= 1;
	 	}
		TOSH_SET_CC_PALE_PIN();
		TOSH_SET_CC_PDATA_PIN();
		TOSH_SET_CC_PCLK_PIN();
		return true;
	}

  //********************************************************/
  // function: read					   */
  // description: accepts a 7 bit address,		   */
  //	creates an array of ones and zeros for each, and   */
  //	uses a loop counting thru the arrays to get	   */
  //	consistent timing for the chipcon radio control	   */
  //	interface.  PALE active low, followed by 7 bits	   */
  //	msb first of address, then lsb low for read	   */
  //	cycle, followed by 8 bits of data msb first.  data */
  //	is clocked in on the falling edge of PCLK.         */
  // Input:  7 bit address				   */
  // Output:  8 bit data				   */
  //********************************************************/

	method read(addr: 8): 8 {
		local cnt: int;
		local din: int;
		local data: 8 = 0x00;
		// address cycle starts here
		addr <<= 1;
		TOSH_CLR_CC_PALE_PIN();  // enable PALE
		for (cnt = 0; cnt < 7; cnt++) {  // send addr PDATA msb first
			if (addr[7] == 0b1) TOSH_SET_CC_PDATA_PIN();
			else TOSH_CLR_CC_PDATA_PIN();
			TOSH_CLR_CC_PCLK_PIN();   // toggle the PCLK
			TOSH_SET_CC_PCLK_PIN();
			addr <<= 1;
		}
		TOSH_CLR_CC_PDATA_PIN();
		TOSH_CLR_CC_PCLK_PIN();   // toggle the PCLK
		TOSH_SET_CC_PCLK_PIN();

		TOSH_MAKE_CC_PDATA_INPUT();  // read data from chipcon
		TOSH_SET_CC_PALE_PIN();  // disable PALE
	

		// data cycle starts here
		for (cnt = 7; cnt >= 0; cnt--) { // send data PDATA msb first
			TOSH_CLR_CC_PCLK_PIN();  // toggle the PCLK
			din = TOSH_READ_CC_PDATA_PIN();
			data = data << 1;
			if(din != 0) data[0] = 0b1;
			else data[0] = 0b0;
			TOSH_SET_CC_PCLK_PIN();
		}	

		TOSH_SET_CC_PALE_PIN();
		TOSH_MAKE_CC_PDATA_OUTPUT();
		TOSH_SET_CC_PDATA_PIN();

		return data;
	}

	method GetLOCK(): 8 {
		return TOSH_READ_CC_CHP_OUT_PIN()::8;
	}
}
