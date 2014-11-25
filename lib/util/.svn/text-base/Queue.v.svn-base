/**
 * Queue implementation. Implements a buffer of fixed size for storing values of a 
 * particular type. Attempts to enqueue more than N values return false, and 
 * attempts to dequeue when the queue is empty return the null value.
 *
 * @author Ryan Hall, Ben L. Titzer
 */
class Queue<T> {
	field none: T;
	field data: T[];
	field head: int;
	field used: int;

	constructor(size: int) {
		data = new T[size];
		head = 0;
		used = 0;
	}

	method enqueue(c: T): boolean {
		if ( full() ) return false;
		data[wrap(head + used)] = c;
		used++;
		return true;
	}

	method dequeue(): T {
		if ( empty() ) return none;
		local c = data[head];
		head = wrap(head + 1);
		used--;
		return c;
	}

	method enqueueArray(buf: T[], i: int, len: int): int {
		local pos = wrap(head + used);
		local cnt = data.length - used;
		if ( len < cnt ) cnt = len;
		used = used + cnt;
		if ( pos < head ) {
			ArrayUtil.copy(buf, data, i, pos, cnt);
			return cnt;
		} else if ( pos > head ) {
			local p = ArrayUtil.copyPartial(buf, data, i, pos, cnt);
			ArrayUtil.copy(buf, data, i + p, 0, cnt - p);
			return cnt;
		}
		return 0;
	}

	method dequeueArray(buf: T[], i: int, len: int): int {
		local max = wrap(head + used);
		local cnt = used;
		if ( len < cnt ) cnt = len;
		used -= cnt;
		if ( head < max ) {
			ArrayUtil.copy(data, buf, head, i, cnt);
			head += cnt;
			return cnt;
		} else if ( head > max ) {
			local p = ArrayUtil.copyPartial(data, buf, head, i, cnt);
			ArrayUtil.copy(data, buf, 0, i + p, cnt - p);
			head = cnt - p;
			return cnt;
		}
		return 0;
	}

	method size(): int {
		return used;
	}

	method full(): boolean {
		return used == data.length;
	}

	method empty(): boolean {
		return used == 0;
	}

	private method wrap(i: int): int {
		if ( i >= data.length ) return i - data.length;
		return i;
	}
}
