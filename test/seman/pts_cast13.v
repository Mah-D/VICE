// @Harness: v2-seman
// @Result: PASS

class A<X> { } 
class B<X> extends A<int> { }

class cast13 {
   method m(x: A<int>) {
      local f = x :: (B<char>);
   }
}
