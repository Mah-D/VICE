// @Harness: v2-seman
// @Test: class inheritance
// @Result: CannotOverrideParamType @ 9:29

class inh27_a {
    method testm(x: int, y: int): int;
}
class inh27_b extends inh27_a {
    method testm(x: int, y: bool): int;
}
