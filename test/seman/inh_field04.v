// @Harness: v2-seman
// @Result: PASS

class inh_field04_a extends inh_field04_b {
  field h: int;
  field j: bool;
}

class inh_field04_b extends inh_field04_c {
}

class inh_field04_c {
  field f: int;
  field g: bool;
}

component inh_field04_d {
  method m(o: inh_field04_a) {
    local x1 = o.f;
    local x2 = o.g;
    local x3 = o.h;
    local x4 = o.j;
  }
}
