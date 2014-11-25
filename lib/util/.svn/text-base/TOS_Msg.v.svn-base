/**
 * @author Xiaoli Gong
 * by hacking the CC1000RadioIntM from NesC source code
 */
 
class TOS_Msg {
	field addr: 16;
	field type: 8;
	field group: 8;
	field length: 8;
	field data: 8[];
	field crc: 16;
	field strength: 16;
	field ack: 8;
	field time: 16;
	field sendSecurityMode: 8;
	field receiveSecurityMode: 8;

	constructor() {
		data = new 8[29];
	}

	method setByte(counter: int, value: 8) {
		local temp: 16;
		switch (counter) {
			case (0) {
				temp = this.addr | 0xff00;
				temp = temp & ((value :: 16) << 8);
				addr = temp;
			}
			case (1) {
				temp = addr &0xff00;
				temp = temp | (value :: 16);
				addr = temp;
			}
			case (2) {
				type = value;
			}
			case (3) {
				group= value;
			}
			case (4) {
				length = value;
			}
			case (34) {
				temp = crc|0xff00;
				temp = temp &((value :: 16) << 8);
				crc = temp;
			}
			case (35) {
				temp = crc & 0xff00;
				temp = temp | (value :: 16);
				crc = temp;
			}
			case (36) {
				temp = strength|0xff00;
				temp = temp &((value :: 16) << 8);
				strength = temp; 
			}
			case (37) {
				temp = strength & 0xff00;
				temp = temp | (value :: 16);
				strength = temp;
			}
			case (38) {
				ack= value;
			}
			case (39) {
				temp = time | 0xff00;
				temp = temp & ((value :: 16) << 8);
				time= temp; 
			}
			case (40) {
				temp = time& 0xff00;
				temp = temp | (value :: 16);
				time = temp;
			}
			case (41) {
				sendSecurityMode = value;
			}
			case (42) {
				receiveSecurityMode = value;
			}
			default {
				data[counter - 5] = value;
			}
		}
	}
 
	method getByte(counter: int): 8 {
		local temp: 8 = 0x00;
		switch (counter) {
			case (0) {
				temp = (addr >> 8) :: 8;
			}
			case (1) {
				temp = (addr & 0xff) :: 8;
			}
			case (2) {
				temp = type;
			}
			case (3) {
				temp = group;
			}
			case (4) {
				temp = length;
			}
			case (34) {
				temp = (crc >> 8) :: 8;
			}
			case (35) {
				temp = (crc & 0xff) :: 8;
			}
			case (36) {
				temp = (strength >> 8) :: 8;
			}
			case (37) {
				temp = (strength & 0xff) :: 8;
			}
			case (38) {
				temp = ack;
			}
			case (39) {
				temp = (time >> 8) :: 8;
			}
			case (40) {
				temp = (time & 0xff) :: 8;
			}
			case (41) {
				temp = sendSecurityMode;
			}
			case (42) {
				temp = receiveSecurityMode;
			}
			default {
				temp = data[counter - 5];
			}
		}

		return temp;
	} 
}
