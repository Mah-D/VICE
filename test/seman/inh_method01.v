// @Harness: v2-seman
// @Result: PASS

class inh_method01_a extends inh_method01_b {
  field h: int = f();
  field j: bool = g();
}

class inh_method01_b extends inh_method01_c {
  method f(): int { return 0; }
  method g(): bool { return false; }
}

class inh_method01_c {
}
