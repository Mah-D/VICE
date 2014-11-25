package vpc.dart;

import java.util.Random;

/**
 * Special random number generator provides uniform possibility for different number of digits
 * @author gbb21
 *
 */
public class RandomGenerator {
	protected final int MaxDigitSize = 6; 
	protected Random m_random = new Random();
	protected static RandomGenerator s_ins = new RandomGenerator();
	
	public static RandomGenerator getIns() {
		return s_ins;
	}
	public int nextInt(int max) {
//		return (int)((m_random.nextDouble() * 2 - 1) * 10 * Math.pow(10, m_random.nextInt(MaxDigitSize)));
		
		// based on mahdi's exponential distribution, lembda = 0.001
		return (int)(-Math.log(m_random.nextDouble()) / 0.001) % max;
	}
	public int nextInt() {
		return nextInt(Integer.MAX_VALUE) * (m_random.nextBoolean() ? 1 : -1);
	}
	public char nextChar() {
		return (char)m_random.nextInt(256);
	}
	
	public static void main(String[] args) {
		RandomGenerator generator = new RandomGenerator();
		for(int i = 0; i < 100; i++)
			System.out.println(generator.nextInt());
	}
}
