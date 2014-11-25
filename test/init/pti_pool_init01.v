// @Harness: v2-init
// @Test: static method invocations
// @Result: PASS

class Pool<T> {
    field array: T[];

    constructor(size: int, nf: function(): T) {
	array = new T[size];
	local i: int;
	for ( i = 0; i < size; i++ ) array[i] = nf();
    }

}

component pool_init01 {
    field a: Pool<int[]> = new Pool<int[]>(3, newArray);

    method newArray(): int[] {
	return new int[0];
    }
}

/* 
heap {
    record #0:1:pool_init01 {
        field a: Pool<int[]> = #1:Pool<int[]>;
    }
    record #1:1:Pool<int[]> {
	field array: int[] = #2:int[][3];
    }
    record #2:3:int[][3] {
	field 0: int[] = #3:int[0];
	field 1: int[] = #4:int[0];
	field 2: int[] = #5:int[0];
    }
    record #3:0:int[0] {
    }
    record #4:0:int[0] {
    }
    record #5:0:int[0] {
    }
} 
*/
