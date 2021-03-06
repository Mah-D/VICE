/*
 * The BubbleSort program implements the common but slow "bubblesort"
 * algorithm to measure runtime impact of various optimizations.
 *
 * @author Akop Palyan
 */
program BubbleSort {
	entrypoint main = BubbleSort.start;
	entrypoint usart0_rx = USART0.rec_handler;
	entrypoint usart0_tx = USART0.tran_handler;
}

component BubbleSort {
	field array: int[] = new int[5];

	constructor() {
		local i: int;

		for (i = 0; i < array.length; i++) {
			array[i] = array.length - i;
		}
	}

	method start() {
		Mica2.startTerminal();
		bubblesort();
		print();
		MCU.sleepForever();
	}

	method bubblesort() {
		local i: int;
		local j: int;
		local tmp: int;

		for (i = 0; i < array.length - 1; i++) {
			for (j = 0; j < array.length - 1; j++) {
				if (array[j] > array[j+1]) {
					tmp = array[j];
					array[j] = array[j+1];
					array[j+1] = tmp;
				}
			}
		}
	}

	method print() {
		local i: int;

		for (i = 0; i < array.length; i++) {
			Terminal.printInt(array[i]);
			Terminal.nextLine();
		}
	}
}
