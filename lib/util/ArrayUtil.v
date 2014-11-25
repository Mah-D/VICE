/**
 * Utility functions for dealing with arrays.
 *
 */

component ArrayUtil {

	method copy<T>(s: T[], d: T[], sp: int, dp: int, len: int) {
		while (len-- > 0) d[dp++] = s[sp++];
	}

	method copyPartial<T>(s: T[], d: T[], sp: int, dp: int, len: int): int {
		local cnt = 0;
		while (len - cnt > 0) {
			if (dp + cnt >= d.length) break;
			d[dp + cnt] = s[sp + cnt];
			cnt++;
		}
		return cnt;
	}

	method dup<T>(a: T[]): T[] {
		local r = new T[a.length];
		copy(a, r, 0, 0, a.length);
		return r;
	}

	method search<T>(a: T[], f: function(T): boolean): int {
		local i = 0;
		while (i < a.length) {
			if (f(a[i++])) return i - 1;
		}
		return -1;
	}
}
