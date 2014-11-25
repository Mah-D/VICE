// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class this04_a {
    field th: this04_a;
    method val(a: int): int { th = this; return a+1; }
}

class this04_b extends this04_a {
    method foo(a: int): int { th = this; return this.val(a)+2; }
}

class this04_c extends this04_a {
    method foo(a: int): int { th = this; return this.val(a)+3; }
}

component this04 {
    field a: this04_a = new this04_a();
    field b: this04_b = new this04_b();
    field c: this04_c = new this04_c();
    field av: int = a.val(10);
    field bv: int = b.foo(10);
    field cv: int = c.foo(10);
}

/* 
heap {
    record #0:6:this04 {
        field a: this04_a = #1:this04_a;
        field b: this04_b = #2:this04_b;
        field c: this04_c = #3:this04_c;
        field av: int = int:11;
        field bv: int = int:13;
        field cv: int = int:14;
    }
    record #1:1:this04_a {
	field th: this04_a = #1:this04_a;
    }
    record #2:1:this04_b {
	field th: this04_a = #2:this04_b;
    }
    record #3:1:this04_c {
	field th: this04_a = #3:this04_c;
    }
} */
