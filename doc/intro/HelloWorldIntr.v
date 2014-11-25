program TestMe {
   entrypoint main = TestMe.main;
   entrypoint timer0_comp = TestMe.intr1;
}
component TestMe {
       method main(): void {
               while (true) {
                       computeValue();
                       transmitValue();
               }
       }
       method computeValue(a:int, b:int, c:int): void {
               // do something for a long time
       }
       method transmitValue(): void {
           local buffer:int;
           if (atomic_swap(sending, true)) 
    			return;
           buffer = checks(a,b,c);
           sending = false;
			return buffer;
       }
       field sending : false;
       method intr1(): void {
               transmitValue();
       }
       Method checks(x:int, y:int, z:int):int {
			if(x<100 && y == 0){
            	if(x>0 && z= pow(x,3)+31){
	       			if(x<-5){
                      	return double(-x);
              		}
              		return 5;
          		}
          		return return double(x)
       		}
       	}  
      Method double(x: int):int{
         Return 2*x;
      }
}
