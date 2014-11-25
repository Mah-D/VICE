// @Harness: v2-exec
// @Test: static method invocations
// @Result: 0=42, 1=2, 2=255, 3=0, 4=2, 5=42

component ptex_find03 {

    field iarr: int[] = { 1, 2, 3, 4, 5 };
    field rarr: 5[] = { 0b00001, 0b10000, 0b01000, 0b00010, 0b00100 };

    method main(arg: int): int {
        local ieq: function(int, int): bool = equal;
        local req: function(5, 5): bool = equal;

	if ( arg == 1 ) return find(iarr, 3, ieq);
	if ( arg == 2 ) return find(iarr, 6, ieq);
        if ( arg == 3 ) return find(rarr, 0b00001, req);
        if ( arg == 4 ) return find(rarr, 0b01000, req);
        return 42;
    }

    method find<T>(arr: T[], val: T, eq: function(T, T): bool): int {
        local i = 0;
	for ( ; i < arr.length; i++ ) {
            if ( eq(arr[i], val) ) return i;
        }
        return -1;
    }

    method equal<T>(x: T, y: T): bool {
        return x == y;
    }
}
