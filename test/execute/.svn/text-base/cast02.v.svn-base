// @Harness: v2-exec
// @Test: operation of instanceof construct
// @Result: 0=7, 1=1, 2=3, 3=5, 4=7

class A { }
class B extends A { }
class C extends A { }
class D { }
class E extends D { }

component cast02 {

    field a: A = new A();
    field b: B = new B();
    field c: C = new C();

    method main(arg: int): int {
	local x: A = getObj(arg);
	local r = 0;
	if ( x <: A or x == null ) { local y = x :: A; r += 1; }
	if ( x <: B or x == null ) { local y = x :: B; r += 2; }
	if ( x <: C or x == null ) { local y = x :: C; r += 4; }
	return r;
    }

    method getObj(arg: int): A {
	if ( arg == 1 ) return a;
	if ( arg == 2 ) return b;
	if ( arg == 3 ) return c;
	return null;
    }
}
