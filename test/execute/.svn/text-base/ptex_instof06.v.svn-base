// @Harness: v2-exec
// @Result: 0=0, 1=0, 2=0, 3=0, 4=1, 5=0, 6=0, 7=0

class A<X> { } 
class B<X> extends A<int> { }

component ptex_instof06 {

   field a: A<int> = new A<int>();
   field b: A<int> = new B<int>();
   field c: A<int> = new B<char>();
   field d: A<int> = new B<bool>();
   field e: A<int> = new B<function()>();

   method m(x: A<int>): bool {
      return x <: (B<char>);
   }

   method main(arg: int): bool {
       if ( arg == 1 ) return m(null);
       if ( arg == 2 ) return m(a);
       if ( arg == 3 ) return m(b);
       if ( arg == 4 ) return m(c);
       if ( arg == 5 ) return m(d);
       if ( arg == 6 ) return m(e);
       return false;  
   }
}
