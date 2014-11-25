program HelloWorld {
    entrypoint main = HelloWorld.main;
}

component HelloWorld {
    method main(a: int, b: int, e: int): int {
    	local c = a * 34;
		local d: int;
		switch(c + b) {
			case (20)
				return 1;
			case (-100)
				return 3;
		}
		
		for(d = 0; d < 100; d++) {
			if(b + d == fun(e))
				return d + 4;
		}
		
		if(e > 10000 and b < 10000) {
			return 10000;
		} else {
			if(e + b < 999)
				return 9999;
			else
				return 9998;
		}
	}
	
	method fun(a: int): int {
		local b = a - 1000;
		return b - 3;  
	}
}
