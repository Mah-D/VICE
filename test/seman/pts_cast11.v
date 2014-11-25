// @Harness: v2-seman
// @Result: PASS

class A<X> { } 
class B<X> extends A<X> { }

class cast11 {
   method m(x: A<int>) {
      local f = x :: (B<int>);
   }
}
