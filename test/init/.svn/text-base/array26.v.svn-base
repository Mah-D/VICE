// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component array26 {
    field a_01: (function(): int)[] = { m, m };
    method m(): int {
	return 0;
    }
}

/* 
heap {
    record #0:1:array26 {
        field a_01: (function():int)[] = #1:(function():int)[2];
    }
    record #1:2:(function():int)[2] {
	field 0:function():int = [#0:array26,array26:m()];
	field 1:function():int = [#0:array26,array26:m()];
    }
} */
