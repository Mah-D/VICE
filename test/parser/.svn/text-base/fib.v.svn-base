// @Harness: v2-parse
// @Result: PASS

class fib {

  field MAX: int = 200;
  field title: string = "Fibonacci program - VIRGIL\n";

  method main() {
    local x1 = 1, x2 = 2, cntr: int;

    print(title);

    for ( cntr = 0; cntr < MAX; cntr++ ) {
       local next = x1 + x2;
       print("The next Fibonacci number is: "+next+"\n");
       x1 = x2;
       x2 = next;

       if ( cntr == MAX-1 ) break;
       else continue;
    }
  }
}
