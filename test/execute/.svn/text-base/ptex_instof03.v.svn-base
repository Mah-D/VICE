// @Harness: v2-exec
// @Result: 0=0, 1=0, 2=1, 3=0

class A<X> { } 

component ptex_instof03 {

   field a: A<int> = new A<int>();

   method m<T>(x: A<T>): bool {
      return x <: (A<int>);
   }

   method main(arg: int): bool {
       local meth: function(A<int>): bool = m;
       if ( arg == 1 ) return meth(null);
       if ( arg == 2 ) return meth(a);
       return false;  
   }
}
