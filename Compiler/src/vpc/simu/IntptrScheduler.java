package vpc.simu;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

import cck.util.Util;

import vpc.Interpreter;
import vpc.core.Program;
import vpc.core.ProgramDecl;
import vpc.core.Value;
import vpc.core.base.PrimRaw;
import vpc.core.base.PrimConversion.Raw_Int32;
import vpc.dart.ExternalVariable;
import vpc.dart.PathChecker;
import vpc.dart.symc.SCValue;
import vpc.stack.InterruptGraph;
import vpc.stack.StackSizeMonitor;
import vpc.tir.TIRInterpreter;
import vpc.hil.Device;

public class IntptrScheduler {

	public static final IntptrScheduler Instance = new IntptrScheduler();
	private TIRInterpreter currInterp;
	private Program currProg;
	private PathChecker checker;
	private InterruptGraph intrGraph;
	private StackSizeMonitor monitor;
	private HashMap<String, Register> intrRegs;
	private ExternalVariable ioRegs;
	private HashMap<String, BitDisp> intrBit;
	private LinkedList<String> fireableInts;
	private Stack<ProgramDecl.EntryPoint> interruptCall;
	//private StringBuilder interruptOutput = new StringBuilder(); 
	private boolean initialized;
	
	long endTime, runTime, lastTime;
	
	class BitDisp{
		public String regName;
		public int   regBit;
		public BitDisp(String reg, int bit){
			regName = reg;
			regBit = bit;
		}
		public boolean equals(BitDisp t){
			if(t.regName.matches(regName) && t.regBit == regBit)
				return true;
			else
				return false;
		}
	}
	private Register sysInt;
//	public String getInterruptInfoString() {
//		return interruptOutput.toString();
//	}
	private void getSysInterrupts(){
		// manually add the system enable interrupt
		sysInt = new Register("SREG");
		intrRegs.put("SREG", sysInt);
		
		for(ProgramDecl.EntryPoint e : currProg.programDecl.entryPoints){
			if(e == currProg.programDecl.mainEntry) // skip the main entry
				continue;
			String intName = e.getName();
			System.out.println("get int name == " + intName);
			Device.Interrupt intr = currProg.targetDevice.interrupts.get(intName);
			BitDisp bit = new BitDisp(intr.maskReg.image, Integer.parseInt(intr.maskBit.image));
			intrBit.put(intName, bit);
			Register currReg = intrRegs.get(bit.regName);
			// if empty, add to firable set
			if(currReg == null){
				currReg = new Register(bit.regName);
				intrRegs.put(currReg.getName(), currReg);
			}
			// initially, firable
			currReg.setBit(bit.regBit, true);
		}	
		buildFireableList();
	}
	public void restoreAllRegs(RegisterPackage regs) {
		this.intrRegs.clear();
		for(Register cur : regs.getRegisters()) {
			this.intrRegs.put(cur.m_regName, cur);
		}
		buildFireableList();
	}
	
	public RegisterPackage backupAllRegs(String name) {
		// it will be deep copyed
		return new RegisterPackage(name, intrRegs.values().toArray(new Register[0]), checker.getCurrentInput());
	}

	public IntptrScheduler(){
		intrRegs = new HashMap<String,Register>();
		intrBit = new HashMap<String, BitDisp>();
		fireableInts = new LinkedList<String>();
		initialized = false;
		runTime = 0;
	}
	public void initScheduler(TIRInterpreter interp, Program p){

		currInterp = interp;
		currProg = p;

		getSysInterrupts();
		System.out.println("Int Scheduler initialized");
		initialized = true;
		int time = (int)Float.class.cast(Interpreter.options.getOption("seconds").getValue()).floatValue();
		endTime = time <= 0 ? 0 : System.currentTimeMillis() + time * 1000;
		runTime = System.currentTimeMillis();
	}
	private void buildFireableList(){
		fireableInts.clear();
		for(String intr : intrBit.keySet()){
			BitDisp b = intrBit.get(intr);
			if(intrRegs.get(b.regName).getBit(b.regBit)){
				fireableInts.add(intr);
			}
		}
	}
	
	public boolean isTimeout(){
		return endTime > 0 && endTime  < System.currentTimeMillis();
	}
	public long runTime() {
		return System.currentTimeMillis() - runTime; 
	}

	
	private ProgramDecl.EntryPoint getIntFired(){
		if(!isEnable())
			return null;
		String fire = getFirableInt();
		if(fire == null )
			return null;	
		ProgramDecl.EntryPoint intEntry = ProgramDecl.lookupEntryPoint(fire, currProg);
		return intEntry;
	}
	
	private boolean isEnable(){
		return sysInt.getBit(7);
	}
	private void CLI(String intrName) {
		sysInt.setBit(7, false);
		setIntMask(intrName, false);
	}
	private void STI(String intrName){
		sysInt.setBit(7, true);
		setIntMask(intrName, true);
	}
	public void setRegValue(Device.Register r, Value v) {
		if(!initialized)
			return;
		Register currReg = intrRegs.get(r.getName());
		if(currReg != null)
			currReg.setValue(PrimRaw.fromValue(v));
		else
			ioRegs.setValue(r.getName(), v);
	}
	public Value getRegValue(Device.Register r){
		if(!initialized)
			return SCValue.getSCValue(PrimRaw.toValue(8, 0));
		
		Register currReg = intrRegs.get(r.getName());
		if(currReg != null)
			return SCValue.getSCValue(PrimRaw.toValue(Register.BitSize, currReg.getValue()));
		else
			return ioRegs.getValue(r.getName(), r.getType());
	}
	
    public static class TimeupEvent extends Util.Error {

		private static final long serialVersionUID = 1L;

		public TimeupEvent() {
            super("Interpreter running timeup!");
        }
    }
	
	private void setIntMask(String intName, boolean val){
		BitDisp bit = intrBit.get(intName);
		intrRegs.get(bit.regName).setBit(bit.regBit, val);
		buildFireableList();
	}
	
	public void tryInterrupt(){
		if(!initialized)
			return;
		ProgramDecl.EntryPoint e = getIntFired();
		
		if(e != null){
			
			if(firePolicy()){
				CLI(e.getName());
				interruptCall.push(e);
				long enterTime = runTime();
				//System.out.println(String.format("Enter Interrupt %s (function name \"%s\"), at time %d ms", e.getName(), e.method.getName(), enterTime));
				
				System.out.println(String.format(" %d (%s, %d) ", enterTime - lastTime, e.getName(), enterTime));
				lastTime = enterTime;
				RegisterPackage pack = null;
				if(this.checker.isTracing()) {
					pack = new RegisterPackage(e.getName(), intrRegs.values().toArray(new Register[0]), checker.getCurrentInput());
					intrGraph.addConnection(pack, monitor.getCurrentMaxStack());
				}
				this.checker.enterInterrupt(pack);
				currInterp.invokeComponentMethod(e.method, null);
		        this.checker.exitInterrupt();
		        interruptCall.pop();
//		        System.out.print(String.format("Exit Interrupt %s (function name \"%s\"), back to %s (function name \"%s\"), ", 
//		        		e.getName(), e.method.getName(), interruptCall.peek().getName(), interruptCall.peek().method.getName()));
		        long leavingTime = runTime();
		        //System.out.println(String.format("at time %d ms, time consuming %d ms", runTime(), leavingTime - enterTime));
		        System.out.println(String.format(" %d (%s, %d) ",  leavingTime - lastTime, interruptCall.peek().getName(), leavingTime));
		        lastTime = leavingTime;
		        STI(e.getName());
			}
			
		}
	}
	public void initDartEnvi(PathChecker checker, ExternalVariable eVar) {
		this.checker = checker;
		this.ioRegs = eVar;
		
	
	}
	public void initStackChecker(StackSizeMonitor monitor, InterruptGraph graph, String entryPoint) {
		this.intrGraph = graph;
		this.monitor = monitor;
		this.interruptCall = new Stack<ProgramDecl.EntryPoint>();
		this.interruptCall.add(ProgramDecl.lookupEntryPoint(entryPoint, currProg));
		System.out.println(String.format("(%s, %d) ", entryPoint, lastTime = runTime()));
	}

	// Return null to skip this time of interrupt pick up
	private String getFirableInt(){
		int guard;	
		if(fireableInts.size() <= 0)
			return null;
		String fire = null;

		guard= (int)(Math.random() * (fireableInts.size()));
		fire = fireableInts.get(guard);

		return fire;
	}
	
	//TODO: determine if the interrupt can fire or not
	private boolean firePolicy(){
		if(Math.random() > 0.9){
			return true;
		}else{
			return false;
		}
			
	}

}
