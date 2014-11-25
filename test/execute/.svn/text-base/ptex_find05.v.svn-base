// @Harness: v2-exec
// @Test: static method invocations
// @Result: 0=42, 1=2, 2=1, 3=4, 4=NullCheckException, 5=42

component ptex_find05 {

    field farr: (function(): int)[] = { f1, f2, f3, f4, f5 };

    method main(arg: int): int {
	if ( arg == 1 ) return find(farr, f3, equal);
	if ( arg == 2 ) return find(farr, f2, equal);
	if ( arg == 3 ) return find(farr, f5, equal);
	if ( arg == 4 ) return find(farr, null, equal);
        return 42;
    }

    method find<T>(arr: T[], val: T, eq: function(T, T): bool): int {
        local i = 0;
	for ( ; i < arr.length; i++ ) {
            if ( eq(arr[i], val) ) return i;
        }
        return -1;
    }

    method equal(x: function(): int, y: function(): int): bool {
        return x() == y();
    }

    method f1(): int { return 1; }
    method f2(): int { return 2; }
    method f3(): int { return 3; }
    method f4(): int { return 4; }
    method f5(): int { return 5; }
}
