// @Harness: v2-seman
// @Test: inheritance of fields
// @Result: MemberDefinedInSuper @ 9:8

class inh14_a {
    field testf: int;
}
class inh14_b extends inh14_a {
    field testf: char;
}
