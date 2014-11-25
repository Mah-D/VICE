class Pattern {
	field next: Pattern;

	field pattern: 32;
	field concrete: 32;
	field matched: 32;

	field func: function(int);

	constructor(p: 32, c: 32, f: function(int)) {
		pattern = p;
		concrete = c;
		func = f;
	}

	method count(cnt: int[]) {
		local cntr = 0;
		for ( ; cntr < Const.MAX; cntr++ ) {
			if ( unmatched(cntr) ) cnt[cntr]++;
		}
	}

	method length(len: int[]) {
		local cntr = Const.MAX-1;
		local l = 0;
		for ( ; cntr >= 0; cntr-- ) {
			l = unmatched(cntr) ? l + 1 : 0;
			if ( l < len[cntr] ) len[cntr] = l;
		}
	}

	method unmatched(b: int): boolean {
		return concrete[b] == 0b1 and matched[b] == 0b0;
	}

	method value(left: int, mask: 32): 32 {
		return (pattern >> left) & mask;
	}

	method setMatched(left: int, len: int) {
		local max = left + len;
		for ( ; left < max; left++ ) matched[left] = 0b1;
	}
}

class EncodingSet {

	field left: int;
	field length: int;
	field size: int;

	field lenarr: int[];
	field cntarr: int[];

	field head: Pattern; // head of patterns list
	field tail: Pattern; // tail of the patterns list
	field children: EncodingSet[];

	method add(p: Pattern) {
		if ( head == null ) head = tail = p;
		else { tail.next = p; tail = p; }
		p.next = null;
		size++;
	}

	method compute() {
		scan();
		split();
		recurse();
	}

	method recurse() {
		if ( children == null ) return;
		local cntr = 0;
		for ( ; cntr < children.length; cntr++ ) {
			local child = children[cntr];
			if ( child != null ) child.compute();
		}
	}

	method scan() {
		if ( size < 2 ) return;
		local cnt = cntarr = computeCount();
		local len = lenarr = computeLength();
		left = 0;
		local cntr = 0;
		for ( ; cntr < Const.MAX; cntr++ ) {
			if ( cnt[cntr] > cnt[left] ) left = cntr;
		}
		length = len[left];
		if ( cnt[left] != size ) ambiguous();
		if ( length < 1 ) zerolength();
	}

	method ambiguous() {
		(new int[0])[-1] = -1;
	}

	method zerolength() {
		(new int[0])[length] = -1;
	}

	method split() {
		if ( size < 2 or length == 0 ) return;
		children = new EncodingSet[(1 << length)::int];
		local pat = head;
		local m = mask(length);
		while ( pat != null ) {
			local next = pat.next;
			local val = pat.value(left, m) :: int;
			if ( children[val] == null ) 
			children[val] = new EncodingSet();
			children[val].add(pat);
			pat.setMatched(left, length);
			pat = next;
		}
	}

	method computeCount(): int[] {
		local cnt = new int[Const.MAX], pat = head;
		while ( pat != null ) {
			pat.count(cnt);
			pat = pat.next;
		}
		return cnt;
	}

	method computeLength(): int[] {
		local len = new int[Const.MAX], pat = head, cntr = 0;
		for ( ; cntr < Const.MAX; cntr++ ) len[cntr] = Const.MAX-cntr;
		while ( pat != null ) {
			pat.length(len);
			pat = pat.next;
		}
		return len;
	}

	method mask(l: int): 32 {
		return Const.MASK >> (Const.MAX - l);
	}

	method build(): Node {
		if ( children == null ) return new Leaf(head.func);
		local nodes = new Node[children.length];
		local cntr = 0;
		for ( ; cntr < children.length; cntr++ ) {
			if ( children[cntr] != null ) 
			nodes[cntr] = children[cntr].build();
		}
		return new Internal(left, mask(length), new EdgeList(nodes));
	}

}

class Builder {

	field root: EncodingSet = new EncodingSet();
	field err: Node;

	method add(pat: 32, conc: 32, f: function(int)) {
		root.add(new Pattern(pat, conc, f));
	}

	method error(f: function(int)) {
		err = new Leaf(f);
	}

	method build(): Recognizer {
		root.compute();
		return new Recognizer(root.build(), err);
	}
}

component Const {
	field MAX: int = 16;
	field MASK: 32 = 0xffff;
}
