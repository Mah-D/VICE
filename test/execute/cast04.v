// @Harness: v2-exec
// @Test: operation of instanceof construct
// @Result: 0=1, 1=1, 2=1, 3=3, 4=3, 5=5, 6=5, 7=7

class A { }
class B extends A { }
class C extends A { }
class D { }
class E extends D { }

component cast04 {

    field arr: A[] = { new A(), new A(), new A(), new B(), new B(), new C(), new C() };
    field d: D = new D();
    field e: D = new E();

    method main(arg: int): int {
	local x: A = getObj(arg);
	local r = 0;
	if ( x <: A or x == null ) { local y = x :: A; r += 1; }
	if ( x <: B or x == null ) { local y = x :: B; r += 2; }
	if ( x <: C or x == null ) { local y = x :: C; r += 4; }
	return r;
    }

    method getObj(arg: int): A {
	if ( arg < arr.length ) return arr[arg];
	return null;
    }
}
