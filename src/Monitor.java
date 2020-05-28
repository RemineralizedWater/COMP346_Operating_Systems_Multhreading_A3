import java.util.Arrays;

/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Michael Rowe 26101267
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */

	// Value of the number of chopsticks
	private boolean[] sticks;
	// Value of whether someone is talking
	private static boolean talk = false;

	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// Set appropriate number of chopsticks based on the # of philosophers
		// Value of the number of chopsticks, set the same as the # of philosophers (unless only one philosopher)
		if (piNumberOfPhilosophers != 1) {
			sticks = new boolean[piNumberOfPhilosophers];
		}
		// If only one philosopher, set the number of chopsticks to two
		else {
			sticks = new boolean[piNumberOfPhilosophers+1];
		}
		// Use Arrays.fill method to set all elements of sticks array to false
		Arrays.fill(sticks, false);
	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID)
	{
		// Checks if only one philosopher, and sets using chopsticks to true if so
		if(sticks.length == 1) {
			// Sets the left and right sticks as being picked up to true
			sticks[piTID-1] = true;
			sticks[piTID] = true;
		}
		else {
			// If more than one philosopher, checks if the ID is not the same as the number of sticks
			if(piTID != sticks.length) {
				// If the left or right chopstick for this philosopher have been picked up, then loop and wait
				while (sticks[piTID - 1] || sticks[piTID]) {
					// Wait needs to be run in a try/catch to deal with possible InterruptedException errors
					try {
						wait();
					} catch(InterruptedException e) {
						System.err.println("Philosopher.pickUp():");
						DiningPhilosophers.reportException(e);
						System.exit(1);
					}
				}
				// Check if the left or right chopstick for this philosopher haven't been picked up
				if (!(sticks[piTID - 1] || sticks[piTID])) {
					// Sets the left and right sticks as being picked up to true
					sticks[piTID] = true;
					sticks[piTID - 1] = true;
				}
			}
			// If the philosopher is the last in the array, then the right chopstick is set to the beginning of the array
			else {
				// If the left or right chopstick for this philosopher have been picked up, then loop and wait
				while (sticks[piTID - 1] || sticks[0]) {
					// Wait needs to be run in a try/catch to deal with possible InterruptedException errors
					try {
						wait();
					} catch(InterruptedException e) {
						System.err.println("Philosopher.pickUp():");
						DiningPhilosophers.reportException(e);
						System.exit(1);
					}
				}
				// Check if the left or right chopstick for this philosopher hasn't been picked up
				if (!(sticks[piTID - 1] && sticks[0])) {
					// Sets the left and right sticks as being picked up to true
					sticks[piTID - 1] = true;
					sticks[0] = true;
				}
			}
		}
	}

	/**
	 * When a given philosopher's done eating, they put the chopsticks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		// Checks if only one philosopher, and sets using chopsticks to false if so
		if (sticks.length == 1) {
			sticks[piTID-1] = false;
			sticks[piTID] = false;
		}
		else {
			// If more than one philosopher, checks if the ID is not the same as the number of sticks
			if(piTID != sticks.length) {
				sticks[piTID-1] = false;
				sticks[piTID] = false;
			}
			else {
				// If the philosopher is the last in the array, then the right chopstick is set to the beginning of the array
				sticks[piTID-1] = false;
				sticks[0] = false;
			}
		}
		// Once the philosopher is finished putting down their left and right chopsticks, they notify the others
		notifyAll();
	}

	/**
	 * Only one philosopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk()
	{
		// If more than one philosopher, wait needs to be run in a try/catch to deal with possible InterruptedException errors
		if(sticks.length != 1) {
			while(talk) {
				try {
					wait();
				} catch(InterruptedException e) {
					System.err.println("Philosopher.requestTalk():");
					DiningPhilosophers.reportException(e);
					System.exit(1);
				}
			}
			// Once no longer waiting for others to finish talking, the philosopher may talk (and thus limits the others from talking)
			talk = true;
		}
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		// The philosopher notifies others that they may talk, and set the talking variable to false
		notifyAll();
		talk = false;
	}
}

// EOF
