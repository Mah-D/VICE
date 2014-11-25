package vpc.simu;

import java.io.Serializable;
import java.util.Arrays;


public class Register implements Comparable<Register>, Serializable{
	private static final long serialVersionUID = 1L;
	final static int BitSize = 8;
	protected String m_regName;
	protected boolean m_bits[];
	public Register(String name){
		m_regName = name;
		m_bits = new boolean[BitSize];
		// none of the interrupts is masked in the beginning
		Arrays.fill(m_bits, true);
	}
	protected Register(Register one) {
		m_regName = one.m_regName;
		m_bits = one.m_bits.clone();
	}
	public void setValue(long v){
		for( int i = 0;i < BitSize; i++ ){
			m_bits[i] = (v & (1<<i)) != 0;
		}
	}
	public long getValue() {
		long res = 0;
		for(int i = 0; i < BitSize; i++) {
			if(m_bits[i])
				res |= 1L << i;
		}
		return res;
	}
	public boolean getBit(int i){
		return m_bits[i];
	}
	public void setBit(int i, boolean v){
		m_bits[i]=v;
	}
	
	public String getName() {
		return m_regName;
	}
	public Register clone() {
		return new Register(this);
	}
	public int hashCode() {
		return m_regName.hashCode() ^ Arrays.hashCode(m_bits);
	}
	public boolean equals(Object other) {
		if(other instanceof Register) {
			Register oreg = (Register) other;
			return m_regName.equals(oreg.m_regName) && Arrays.equals(m_bits, oreg.m_bits);
		} else
			return false;
	}
	
	public int compareTo(Register o) {
		return m_regName.compareTo(o.m_regName);
	}
	public String toString() {
		return m_regName + " : " + Arrays.toString(m_bits);
	}
}
