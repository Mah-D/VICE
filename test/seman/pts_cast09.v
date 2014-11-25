// @Harness: v2-seman
// @Result: InvalidTypeCast @ 8:19

class A<X> { } 

class cast09 {
   method m(x: A<int>) {
      local f = x :: (A<char>);
   }
}
