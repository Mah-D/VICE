// @Harness: v2-seman
// @Result: PASS

class A<X> { } 

class cast08 {
   method m(x: A<int>) {
      local f = x :: (A<int>);
   }
}
