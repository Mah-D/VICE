class Producer<T> {
	method ready(): boolean {
		return true;
	}

	method produce(): T;
}

class NullProducer<T> extends Producer<T> {
	field value: T;

	constructor(v: T) {
		value = v;
	}

	method produce(): T {
		return value;
	}
}

class ArrayProducer<T> extends Producer<T> {

	field array: T[];
	field pos: int;
	field end: int;

	constructor(a: T[], p: int, len: int) {
		array = a;
		pos = p;
		end = pos + len;
	}

	method ready(): boolean {
		return pos < end;
	}

	method produce(): T {
		if ( ready() ) return array[pos++];
		else return array[end - 1];
	}
}

class ConverterProducer<X, T> extends Producer<T> {
	field producer: Producer<X>;
	field convert: function(X): T;

	constructor(p: Producer<X>, c: function(X): T) {
		producer = p;
		convert = c;
	}

	method ready(): boolean {
		return producer.ready();
	}

	method produce(): T {
		return convert(producer.produce());
	}
}

class AdapterToProducer<T> extends Producer<T> {
	field fready: function(): boolean;
	field fproduce: function(): T;

	constructor(fr: function(): boolean, fp: function(): T) {
		fready = fr;
		fproduce = fp;
	}

	method ready(): boolean { return fready(); }
	method produce(): T { return fproduce(); }
}
