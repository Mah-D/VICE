// @Harness: v2-seman
// @Result: PASS

class inh_field01_a extends inh_field01_b {
  field h: int = f;
  field j: bool = g;
}

class inh_field01_b extends inh_field01_c {
  field f: int;
  field g: bool;
}

class inh_field01_c {
}
