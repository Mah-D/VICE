// @Harness: v2-seman
// @Result: PASS

class inh_method02_a extends inh_method02_b {
  field h: int = f();
  field j: bool = g();
}

class inh_method02_b extends inh_method02_c {
}

class inh_method02_c {
  method f(): int { return 0; }
  method g(): bool { return false; }
}
