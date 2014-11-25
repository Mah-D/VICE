program HelloWorld {
    entrypoint main = HelloWorld.main;
}

component HelloWorld {
    method main(a: int): int {
    	local c = 5 * a;
    	local b = 0;
    	local i: int;
    	local j: int;
    	if(c < 100)
    	{
				for (i = 0; i < 10; i++) {
					for (j = 0; j < 20; j++) {
						a=a*a;
				if ( c==a ) 
					return -5;
			}
		}

		}
		return b;
	}
}
