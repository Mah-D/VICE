// @Harness: v2-seman
// @Result: PASS

class inh_method03_a extends inh_method03_b {
  field h: function(): int = f;
  field j: function(): bool = g;
}

class inh_method03_b extends inh_method03_c {
  method f(): int { return 0; }
  method g(): bool { return false; }
}

class inh_method03_c {
}
