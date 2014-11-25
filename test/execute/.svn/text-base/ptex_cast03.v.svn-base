// @Harness: v2-exec
// @Result: 0=0, 1=1, 2=1, 3=0

class A<X> { } 

component ptex_cast03 {

   field a: A<int> = new A<int>();

   method m<T>(x: A<T>): bool {
      local f = x :: (A<int>);
      return true;
   }

   method main(arg: int): bool {
       local meth: function(A<int>): bool = m;
       if ( arg == 1 ) return meth(null);
       if ( arg == 2 ) return meth(a);
       return false;  
   }
}
