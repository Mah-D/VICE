// @Harness: v2-seman
// @Test: function types (complex)
// @Result: PASS

class object {
  field name: char[];
}

class ht_link {
  field obj: object;
  field link: ht_link;
}

class hashtable {
 
  field size: int;
  field table: ht_link[];

  // this method demonstrates functional-style containers
  method app(f: function(object)) {
     local cntr = 0;
     for ( ; cntr < size; cntr++ ) {
       local head = table[cntr];
       while (head != null) {
         // apply the function we were passed
         f(head.obj);
         head = head.link;
       }
     }
      
  }
}

class test {

  method testm() {
    local ht = new hashtable();
    // demonstration of method extraction: pass the visit method
    ht.app(this.visit);
  }

  method visit(o: object) {
  }

}
