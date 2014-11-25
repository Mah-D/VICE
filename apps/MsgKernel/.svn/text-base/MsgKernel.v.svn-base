/*
 * The MsgKernel program is an adaption of the core message-passing
 * mechanism from the SOS operating system. It demonstrates the use
 * of object concepts in expressing extensibility for messages and
 * modules.
 * @author Simon Han
 */
program MsgKernel {
	entrypoint main = MsgKernel.start;
}

class Message {
	field dest: int;
	field src:  int;
	field dispatch: function(MsgHandler, Message): int;
	
	method send(dst: int, s: int, d: function(MsgHandler, Message): int) : int {
		dest = dst;
		src = s;
		dispatch = d;
		return MsgKernel.msg_send(this);
	}
	method free() {
		MsgPool.free(this);
	}
}

component Dispatch {
	method init(h: MsgHandler, m: Message): int { return h.handleInit(m); }
	method ping(h: MsgHandler, m: Message): int { return h.handlePing(m); }
	method pong(h: MsgHandler, m: Message): int { return h.handlePong(m); }
}

class MsgHandler {
	field id: int;
	
	constructor(i: int) {
		id = i;
		MsgKernel.register(this);
	}

	method set_id(i: int) {
		id = i;
	}

	method get_id() : int {
		return id;
	}

	method handleInit(m: Message): int {
		return 0;
	}

	method handlePing(m: Message): int {
		return 0;
	}

	method handlePong(m: Message): int {
		return 0;
	}
}

component ID {
	field SCHED: int = 0;
	field TEST1: int = 1;
	field TEST2: int = 2;
}

component MSG_TYPE {
	field MSG_INIT: int = 0;
	field MSG_PING: int = 1;
	field MSG_PONG: int = 2;
}

class Module1 extends MsgHandler {
	constructor() : super(ID.TEST1) { }

	method handleInit(m: Message): int {
		local m_out = MsgPool.alloc();
		Mica2.green.toggle();

		if (m_out != null) {
			m_out.send(ID.TEST2, ID.TEST1, Dispatch.ping);
		}
		m.free();
		return 0;
	}

	method handlePong(m: Message): int {
		Mica2.yellow.toggle();
		local m_out = MsgPool.alloc();
		if (m_out != null) {
			m_out.send(ID.TEST2, ID.TEST1, Dispatch.ping);
		}
		m.free();
		return 0;
	}
}


class Module2 extends MsgHandler {
	constructor() : super(ID.TEST2) { }

	method handleInit(m: Message): int {
		Mica2.yellow.toggle();
		m.free();
		return 0;
	}
	method handlePing(m: Message): int {
		local m_out = MsgPool.alloc();
		Mica2.red.toggle();
		if (m_out != null) {
			m_out.send(m.src, ID.TEST2, Dispatch.pong);
		}
		m.free();
		return 0;
	}
}

component MsgPool {
	field NUM_MSG: int = 15;
	field pool: Message[] = new Message[NUM_MSG];
	
	constructor () {
		local i: int;
		for (i = 0; i < NUM_MSG; i++) {
			pool[i] = new Message();
		}
	}

	method alloc() : Message {
		local i: int;
		for (i = 0; i < NUM_MSG; i++) {
			if (pool[i] != null) {
				local tmp: Message = pool[i];
				pool[i] = null;
				return tmp;
			}
		}
		return null;
	}

	method free(m: Message) {
		local i: int;
		for (i = 0; i < NUM_MSG; i++) {
			if (pool[i] == null) {
				pool[i] = m;
				return;
			}
		}
	}
}

component MsgKernel {
	field MAX_MSG: int = 16;
	field MAX_HANDLE: int = 16;
	//! message handler registered to sched
	field hl: MsgHandler[] = new MsgHandler[MAX_HANDLE];
	//! message queue
	field mq: Message[]		 = new Message[MAX_MSG];
	field mq_free: int;
	field mq_full: int;
	
	constructor () {
		local i: int;

		mq_free = 0;
		mq_full = 0;
		new Module1();
		new Module2();
	}

	method register(h: MsgHandler): int {
		local i: int;
		for (i = 0; i < MAX_HANDLE; i++) {
			if (hl[i] == null) {
				local m: Message = MsgPool.alloc();
				hl[i] = h;
				
				if (m != null) {
					m.send(h.id, ID.SCHED, Dispatch.init);
				}
				return 0;
			}
		}
		return -1;
	}

	method msg_send(m: Message): int {
		local tmp: int;
		tmp = mq_free;

		if (mq[tmp] == null) {
			mq_free = (tmp + 1) % MAX_MSG;
			mq[tmp] = m;
			return 0;
		} else {
			return -1;
		}
	}

	method handle_msg(m: Message): int {
		local i: int;
		for (i = 0; i < MAX_HANDLE; i++) {
			if (hl[i] != null and hl[i].get_id() == m.dest) {
				return m.dispatch(hl[i], m);
			}
		}
		return -1;
	}

	method start() {
		local cntr = 0;
		Mica2.startLEDs();
		for (cntr = 0; cntr < 1000; cntr++) {
			local old: int;
			local m: Message;
			old = mq_full;
			
			if (mq[old] != null) {
				m = mq[old];
				mq[old] = null;
				mq_full = (old + 1) % MAX_MSG;

				// Handling message
				handle_msg(m);
			}
		}
	}
}

