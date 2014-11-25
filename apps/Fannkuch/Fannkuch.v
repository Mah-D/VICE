/*
 * This program is adapted from the programming language shootout
 * benchmarks and is used to measure the impact of various compiler
 * optimizations on run time performance. The primary operations
 * are array permutations.
 * @author Ben L. Titzer
 */
program Fannkuch {
	entrypoint main = Fannkuch.start;
	entrypoint usart0_rx = USART0.rec_handler;
	entrypoint usart0_udre = USART0.tran_handler;
}

component Fannkuch {
	field MAX: int = 20;

	field fperm: int[] = new int[MAX];
	field fperm1: int[] = new int[MAX];
	field fcount: int[] = new int[MAX];
	field fmaxPerm: int[] = new int[MAX];

	method fannkuch(n: int): int {
		local check = 0;
		local maxFlipsCount = 0;
		local m = n - 1;
		local i: int, j: int;

		local perm = fperm;						  // FUNCTIONALITY: runtime allocation
		local perm1 = fperm1;
		local count = fcount;
		local maxPerm = fmaxPerm;

		for (i = 0; i < n; i++) perm1[i] = i;

		local r = n;

		if (true) {
			// write-out the first 30 permutations
			if (check < 30) {
			  for(i = 0; i < n; i++) Terminal.printInt(perm1[i]+1);
			  Terminal.nextLine();
			  check++;
			}

			while (r != 1) { 
				count[r - 1] = r; 
				r--; 
			}

			if (!(perm1[0] == 0 or perm1[m] == m)) {
				copy(perm, perm1, n);				 // OPTIMIZATION: inlining

				local flipsCount = 0;
				local k = perm[0];

				while (k != 0) {
					local k2 = (k + 1) / 2;		   // OPTIMIZATION: strength reduction
					for (j = 0; j < k2; j++) {
						local temp = perm[j]; 
						perm[j] = perm[k - j]; 
						perm[k - j] = temp;
					}
					flipsCount++;
					k = perm[0];
				}

				if (flipsCount > maxFlipsCount) {
					maxFlipsCount = flipsCount;
					copy(maxPerm, perm1, n);		  // OPTIMIZATION: inlining
				}
			}

			if (true) {
				if (r == n) return maxFlipsCount;
				local perm0 = perm1[0];
				i = 0;
				while (i < r) {
					perm1[i] = perm1[i + 1];
					i = i + 1;
				}
				perm1[r] = perm0;	

				count[r] = count[r] - 1;
				if (count[r] > 0) break;
				r++;
			}
		}
		return maxFlipsCount;
	}

	method start() {
		Mica2.startTerminal();
		fannkuch(5);
	}

	method main(args: char[][]): int {
		return fannkuch(atoi(args[0]));
	}

	method copy(a: int[], b: int[], n: int) {
		local j: int;
		for (j = 0; j < n; j++) a[j] = b[j];
	}

	method atoi(a: char[]): int {
		local accum = 0;
		local cntr = 0;
		local zero = '0'::int;

		for ( ; cntr < a.length; cntr++ ) {
			local dig = a[cntr]::int;
			accum = accum * 10 + dig - zero; 
		}

		return accum;
	}
}
