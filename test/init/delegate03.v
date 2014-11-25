// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class dg03_a {
    method getf(): dg03_a { return this; }
}

class dg03_b extends dg03_a {
}

class dg03_c extends dg03_a {
}

component dg03 {
    field a: function():dg03_a = new dg03_a().getf;
    field b: function():dg03_a = new dg03_b().getf;
    field c: function():dg03_a = new dg03_c().getf;
    field av: dg03_a = a();
    field bv: dg03_a = b();
    field cv: dg03_a = c();
}

/* 
heap {
    record #0:6:dg03 {
        field a: function():dg03_a = [#1:dg03_a,dg03_a:getf()];
        field b: function():dg03_a = [#2:dg03_b,dg03_a:getf()];
        field c: function():dg03_a = [#3:dg03_c,dg03_a:getf()];
        field av: dg03_a = #1:dg03_a;
        field bv: dg03_a = #2:dg03_b;
        field cv: dg03_a = #3:dg03_c;
    }
    record #1:0:dg03_a {
    }
    record #2:0:dg03_b {
    }
    record #3:0:dg03_c {
    }
} */
