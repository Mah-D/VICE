// @Harness: v2-seman
// @Test: class inheritance
// @Result: CyclicInheritance @ 4:22 , 8:22

class inh3_a extends inh3_b {
    field testf: int;
}
class inh3_b extends inh3_a {
    field bar: int;
}
