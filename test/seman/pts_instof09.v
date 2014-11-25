// @Harness: v2-seman
// @Result: InvalidTypeQuery @ 8:19

class A<X> { } 

class instof09 {
   method m(x: A<int>) {
      local f = x <: (A<char>);
   }
}
