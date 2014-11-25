program HelloWorld {
    entrypoint main = HelloWorld.main;
    
}

component HelloWorld {
    constructor() {
        Mica2.setTimerCompare(Mica2.red.toggle);
    }
    method main(a: int, b: int, e: int): int {
    
        local c = a * 34;


        if(fun(a) > c)
		return 2;
	else
      
	return 1;        

    }
    

    method fun(a: int): int {
        local b = a - 1000;
	if(a < 100)
        return fun2(a);
	else
	return 0 ;
    }

    method fun2(a: int): int {
        local b = a + 1000;
	if(a < 10)
        return fun3(a);
	else
	return 0 ;

    }

    method fun3(a: int): int {
        local b = a *500;
	if(a < 1)
        return b;
	else
	return 0 ;

    }

}