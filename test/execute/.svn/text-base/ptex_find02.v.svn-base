// @Harness: v2-exec
// @Test: static method invocations
// @Result: 0=42, 1=2, 2=255, 3=0, 4=2, 5=42

component ptex_find02 {

    field iarr: int[] = { 1, 2, 3, 4, 5 };
    field rarr: 5[] = { 0b00001, 0b10000, 0b01000, 0b00010, 0b00100 };

    method main(arg: int): int {
	if ( arg == 1 ) return find(iarr, 3, equal_int);
	if ( arg == 2 ) return find(iarr, 6, equal_int);
        if ( arg == 3 ) return find(rarr, 0b00001, equal_5);
        if ( arg == 4 ) return find(rarr, 0b01000, equal_5);
        return 42;
    }

    method find<T>(arr: T[], val: T, equal: function(T, T): bool): int {
        local i = 0;
	for ( ; i < arr.length; i++ ) {
            if ( equal(arr[i], val) ) return i;
        }
        return -1;
    }

    method equal_int(x: int, y: int): bool {
        return x == y;
    }

    method equal_5(x: 5, y: 5): bool {
        return x == y;
    }
}
