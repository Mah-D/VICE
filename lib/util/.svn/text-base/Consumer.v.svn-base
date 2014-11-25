class Consumer<T> {
	method ready(): boolean {
		return true;
	}

	method consume(v: T);
}

class NullConsumer<T> extends Consumer<T> {

	method consume(v: T) {
		// do nothing.
	}
}

class AdapterToConsumer<T> extends Consumer<T> {
	field fready: function(): boolean;
	field fconsume: function(T);

	constructor(fr: function(): boolean, fp: function(T)) {
		fready = fr;
		fconsume = fp;
	}

	method ready(): boolean { return fready(); }
	method consume(v: T) { fconsume(v); }
}
