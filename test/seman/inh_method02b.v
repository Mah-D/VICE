// @Harness: v2-seman
// @Result: PASS

class inh_method02b_a extends inh_method02b_b {
  field h: int = this.f();
  field j: bool = this.g();
}

class inh_method02b_b extends inh_method02b_c {
}

class inh_method02b_c {
  method f(): int { return 0; }
  method g(): bool { return false; }
}
