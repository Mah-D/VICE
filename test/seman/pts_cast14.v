// @Harness: v2-seman
// @Result: PASS

class A<X> { } 
class B<X> extends A<int> { }

class cast14 {
   method m<T>(x: A<T>) {
      local f = x :: (B<char>);
   }
}
