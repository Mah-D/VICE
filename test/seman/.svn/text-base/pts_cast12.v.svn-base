// @Harness: v2-seman
// @Result: InvalidTypeCast @ 9:19

class A<X> { } 
class B<X> extends A<X> { }

class cast12 {
   method m(x: A<int>) {
      local f = x :: (B<char>);
   }
}
