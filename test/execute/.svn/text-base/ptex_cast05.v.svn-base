// @Harness: v2-exec
// @Result: 0=0, 1=1, 2=TypeCheckException, 3=TypeCheckException, 4=1, 5=TypeCheckException, 6=0

class A<X> { } 
class B<X> extends A<int> { }

component ptex_cast05 {

   field a: A<int> = new A<int>();
   field b: A<int> = new B<int>();
   field c: A<int> = new B<char>();
   field d: A<int> = new B<bool>();

   method m(x: A<int>): bool {
      local f = x :: (B<char>);
      return true;
   }

   method main(arg: int): bool {
       if ( arg == 1 ) return m(null);
       if ( arg == 2 ) return m(a);
       if ( arg == 3 ) return m(b);
       if ( arg == 4 ) return m(c);
       if ( arg == 5 ) return m(d);
       return false;  
   }
}
