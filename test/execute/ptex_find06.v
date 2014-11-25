// @Harness: v2-exec
// @Test: static method invocations
// @Result: 0=42, 1=2, 2=1, 3=4, 4=255, 5=0, 6=1, 7=255, 8=42

component ptex_find06 {

    field farr: (function(): int)[] = { f1, f2, f3, f4, f5 };
    field carr: (function(): char)[] = { fA, fB, fC };

    method main(arg: int): int {
	if ( arg == 1 ) return find(farr, 3);
	if ( arg == 2 ) return find(farr, 2);
	if ( arg == 3 ) return find(farr, 5);
	if ( arg == 4 ) return find(farr, 7);
	if ( arg == 5 ) return find(carr, 'A');
	if ( arg == 6 ) return find(carr, 'B');
	if ( arg == 7 ) return find(carr, 'D');
        return 42;
    }

    method find<T>(arr: (function(): T)[], val: T): int {
        local i = 0;
	for ( ; i < arr.length; i++ ) {
            if ( arr[i]() == val ) return i;
        }
        return -1;
    }

    method f1(): int { return 1; }
    method f2(): int { return 2; }
    method f3(): int { return 3; }
    method f4(): int { return 4; }
    method f5(): int { return 5; }

    method fA(): char { return 'A'; }
    method fB(): char { return 'B'; }
    method fC(): char { return 'C'; }
}
