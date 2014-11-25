// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class this05_a {
    method getf(): function(): int { return this.val; }
    method val(): int { return 1; }
}

class this05_b extends this05_a {
    method val(): int { return 2; }
}

class this05_c extends this05_a {
    method val(): int { return 3; }
}

component this05 {
    field a: function():int = new this05_a().getf();
    field b: function():int = new this05_b().getf();
    field c: function():int = new this05_c().getf();
    field av: int = a();
    field bv: int = b();
    field cv: int = c();
}

/* 
heap {
    record #0:6:this05 {
        field a: function():int = [#1:this05_a,this05_a:val()];
        field b: function():int = [#2:this05_b,this05_b:val()];
        field c: function():int = [#3:this05_c,this05_c:val()];
        field av: int = int:1;
        field bv: int = int:2;
        field cv: int = int:3;
    }
    record #1:0:this05_a {
    }
    record #2:0:this05_b {
    }
    record #3:0:this05_c {
    }
} */
