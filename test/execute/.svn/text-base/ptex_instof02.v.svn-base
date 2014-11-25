// @Harness: v2-exec
// @Result: 0=0, 1=0, 2=1, 3=0

class A<X> { } 

component ptex_instof02 {

   field a: A<int> = new A<int>();
   field b: A<char> = new A<char>();

   method m(x: A<int>): bool {
      return x <: (A<int>);
   }

   method main(arg: int): bool {
       if ( arg == 1 ) return m(null);
       if ( arg == 2 ) return m(a);
       return false;  
   }
}
