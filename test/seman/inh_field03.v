// @Harness: v2-seman
// @Result: PASS

class inh_field03_a extends inh_field03_b {
  field h: int;
  field j: bool;
}

class inh_field03_b extends inh_field03_c {
  field f: int;
  field g: bool;
}

class inh_field03_c {
}

component inh_field03_d {
  method m(o: inh_field03_a) {
    local x1 = o.f;
    local x2 = o.g;
    local x3 = o.h;
    local x4 = o.j;
  }
}
