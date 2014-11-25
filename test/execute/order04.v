// @Harness: v2-exec
// @Test: order of evaluation
// @Result: 0=0, 1=1, 2=2, 3=3, 4=3, 5=0

class order04_a {
    field foo: int;
}

component order04 {
    field order: int[] = { 0, 0 };
    field pos: int = 0;
    field obj: order04_a = new order04_a();

    method first(): order04_a {
        order[pos] = 1;
        pos++;
        return obj;
    }

    method second(a: int): int {
        order[pos] = 2;
        pos++;
        return a;
    }

    method main(arg: int): int {
        local result = first().foo = second(3);
	if ( arg == 1 ) return order[0];
	if ( arg == 2 ) return order[1];
	if ( arg == 3 ) return result;
	if ( arg == 4 ) return obj.foo;
	return 0;
    }

}
