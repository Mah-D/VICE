// @Harness: v2-seman
// @Test: return from non-void method must have value
// @Result: VoidReturn @ 21:13

class fib {
    field MAX: int = 200;
    field title: string = "Fibonacci program - VIRGIL\n";
    
    method main(): int {
        local x1: int = 1,
              x2: int = 2;
        local cntr: int;

        print(title);

        for ( cntr = 0; cntr < MAX; cntr ) {
            local next: int = x1 + x2;

            print("The next Fibonacci number is: " + next + "\n");

            x1 = x2;
            x2 = next;

            if ( cntr == MAX - 1 ) break;
            else return;
        }
        return 0;
    }
}
