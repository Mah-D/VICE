// @Harness: v2-seman
// @Result: PASS

class inh_field07_a extends inh_field07_b {
  field h: int = f;
  field j: bool = g;
}

class inh_field07_b extends inh_field07_c<int, bool> {
}

class inh_field07_c<Y, Z> {
  field f: Y;
  field g: Z;
}
