// @Harness: v2-seman
// @Result: PASS

class inh_method04_a extends inh_method04_b {
  field h: function(): int = f;
  field j: function(): bool = g;
}

class inh_method04_b extends inh_method04_c {
}

class inh_method04_c {
  method f(): int { return 0; }
  method g(): bool { return false; }
}
