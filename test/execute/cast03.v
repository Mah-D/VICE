// @Harness: v2-exec
// @Test: operation of instanceof construct
// @Result: 0=3, 1=1, 2=3, 3=3, 4=3

class A { }
class B extends A { }
class C extends A { }
class D { }
class E extends D { }
class F { }

component cast03 {

    field a: A = new A();
    field d: D = new D();
    field e: E = new E();

    method main(arg: int): int {
	local x = getObj(arg);
	local r = 0;
	if ( x <: D or x == null ) { local y = x :: D; r += 1; }
	if ( x <: E or x == null ) { local y = x :: E; r += 2; }
	return r;
    }

    method getObj(arg: int): D {
	if ( arg == 1 ) return d;
	if ( arg == 2 ) return e;
	return null;
    }
}
