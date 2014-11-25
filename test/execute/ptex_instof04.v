// @Harness: v2-exec
// @Result: 0=0, 1=0, 2=0, 3=1, 4=1, 5=0

class A<X> { } 
class B<X> extends A<X> { }

component ptex_instof04 {

   field a: A<int> = new A<int>();
   field b: A<int> = new B<int>();
   field c: A<int> = new B<int>();

   method m(x: A<int>): bool {
      return x <: (B<int>);
   }

   method main(arg: int): bool {
       if ( arg == 1 ) return m(null);
       if ( arg == 2 ) return m(a);
       if ( arg == 3 ) return m(b);
       if ( arg == 4 ) return m(c);
       return false;  
   }
}
