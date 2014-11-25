package vpc.simu;

import java.io.Serializable;
import java.util.Arrays;

import vpc.dart.symc.SCValue;


public class RegisterPackage implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Register[] m_regs;
	protected SCValue[] m_backTrace;
	protected String m_entryName;
	public RegisterPackage(String entryName, Register[] regs) {
		assert regs != null;
		m_regs = new Register[regs.length];
		for(int i = 0; i < regs.length; i++)
			m_regs[i] = regs[i].clone();
		m_entryName = entryName;
		Arrays.sort(m_regs);
	}
	public RegisterPackage(String entryName, Register[] regs, SCValue[] vals) {
		this(entryName, regs);
		if(vals != null) {
			m_backTrace = new SCValue[vals.length];
			for(int i = 0; i < vals.length; i++)
				m_backTrace[i] = vals[i];
		}
	}
	
	public SCValue[] getInput() {
		return m_backTrace;
	}
	public Register[] getRegisters() {
		return m_regs;
	}
	
	public int hashCode() {
		return m_entryName.hashCode() ^ Arrays.deepHashCode(m_regs);
	}
	public String getEntryName() {
		return m_entryName;
	}
	
	public boolean equals(Object o) {
		if(o instanceof RegisterPackage) {
			RegisterPackage other = (RegisterPackage)o;
			return m_entryName.equals(other.m_entryName) && Arrays.equals(m_regs, other.m_regs);
		}
		else
			return false;
	}
}
