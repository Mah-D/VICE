// @Harness: v2-parse
// @Result: PASS

class if2 {
  method m() {
    local a: int = 0;
    
    if ( true ) { }
    if ( func(a) ) { }

    if ( true ) { } else  { }
    if ( func(a) ) { } else  { }

    if ( true ) { a = 0; } else { a = 1; }
    if ( func(a) ) { a = 0; } else { a = 1; }

    if ( true ) { local b = 0; } else { local c = 1; }
    if ( func(a) ) { local b = 0; } else { local c = 1; }
  }
}
