// @Harness: v2-seman
// @Result: PASS

class A<X> { } 

class instof10 {
   method m<T>(x: A<T>) {
      local f = x <: (A<int>);
   }
}
