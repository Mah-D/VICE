// @Harness: v2-init
// @Test: static method invocations
// @HeapGC: false
// @Result: PASS

component map01 {
    field a: int[] = { 1, 2, 3, 4, 5 };
    field b: int[] = map(a, hash, 5);

    method hash(a: int): int {
	return a * 11;
    }

    // iterative version of fold
    method map(a: int[], f: function(int): int, m: int): int[] {
        local res = new int[m];
        local cntr = 0;
        for ( ; cntr < m; cntr++ ) res[cntr] = f(a[cntr]);
	return res; 
    }
}

/* 
heap {
    record #0:2:map01 {
        field a: int[] = #1:int[5];
        field b: int[] = #2:int[5];
    }
    record #1:5:int[5] {
        field 0: int = int:1;
        field 1: int = int:2;
        field 2: int = int:3;
        field 3: int = int:4;
        field 4: int = int:5;
    }
    record #2:5:int[5] {
        field 0: int = int:11;
        field 1: int = int:22;
        field 2: int = int:33;
        field 3: int = int:44;
        field 4: int = int:55;
    }
} */
