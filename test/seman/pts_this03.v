// @Harness: v2-seman
// @Result: PASS

class this03_a<X> {
  method m() {
     this03_b.test(this);
  }
}

component this03_b {
  method test<Y>(o: this03_a<Y>) {
  }
}
