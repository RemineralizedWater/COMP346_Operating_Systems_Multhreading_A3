/**
 * Class DiningPhilosophers
 * The main starter.
 *
 * @author Michael Rowe 26101267
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class DiningPhilosophers
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */

	/**
	 * This default may be overridden from the command line
	 */
	public static final int DEFAULT_NUMBER_OF_PHILOSOPHERS = 4;

	/**
	 * Dining "iterations" per philosopher thread
	 * while they are socializing there
	 */
	public static final int DINING_STEPS = 10;

	/**
	 * Our shared monitor for the philosophers to consult
	 */
	public static Monitor soMonitor = null;

	/*
	 * -------
	 * Methods
	 * -------
	 */

	/**
	 * Main system starts up right here
	 */
	public static void main(String[] argv)
	{
		try
		{
			/*
			 * Should be settable from the command line
			 * or the default if no arguments supplied.
			 * Also able to handle running 'make regression' from command line
			 * or manual variable command line entries
			 */
			int iPhilosophers = DEFAULT_NUMBER_OF_PHILOSOPHERS;

			// Checks if the numbers of arguments is greater than 0
			if (argv.length > 0) {
				// Run try-catch to catch invalid parseInt format (requires string in integer format)
				try {
					// Attempts to store command line string argument as a parsed int for the number of philosophers
					iPhilosophers = Integer.parseInt(argv[0]);
					// Checks for non-positive integers
					if (iPhilosophers <= 0) {
						// Display output informing of improper format, then exit -1
						System.out.println("\""+ argv[0] + "\"" + " is not a positive decimal integer\n");
						System.out.println("Usage: java DiningPhilosophers [NUMBER_OF_PHILOSOPHERS]");
						System.exit(-1);
					}
					// Checks for too many arguments
					else if (argv.length > 1) {
						// Display output informing of improper format, then exit -1
						System.out.println("Improper number of arguments\n");
						System.out.println("Usage: java DiningPhilosophers [NUMBER_OF_PHILOSOPHERS]");
						System.exit(-1);
					}
				}
				// Catches NumberFormatException when parsed command line string is not an int
				catch (NumberFormatException e) {
					// Display output informing of improper format, then exit -1
					System.out.println("\""+ argv[0] + "\"" + " is not a positive decimal integer\n");
					System.out.println("Usage: java DiningPhilosophers [NUMBER_OF_PHILOSOPHERS]");
					System.exit(-1);
				}
			}

			// Make the monitor aware of how many philosophers there are
			soMonitor = new Monitor(iPhilosophers);

			// Space for all the philosophers
			Philosopher aoPhilosophers[] = new Philosopher[iPhilosophers];

			// Moved print statement to announce dinner starting to before threads start
			System.out.println
					(
							iPhilosophers +
									" philosopher(s) came in for a dinner."
					);

			// Let 'em sit down
			for(int j = 0; j < iPhilosophers; j++)
			{
				aoPhilosophers[j] = new Philosopher();
				aoPhilosophers[j].start();
			}

			// Main waits for all its children to die...
			// I mean, philosophers to finish their dinner.
			for(int j = 0; j < iPhilosophers; j++)
				aoPhilosophers[j].join();

			System.out.println("All philosophers have left. System terminates normally.");
		}
		catch(InterruptedException e)
		{
			System.err.println("main():");
			reportException(e);
			System.exit(1);
		}
	} // main()

	/**
	 * Outputs exception information to STDERR
	 * @param poException Exception object to dump to STDERR
	 */
	public static void reportException(Exception poException)
	{
		System.err.println("Caught exception : " + poException.getClass().getName());
		System.err.println("Message          : " + poException.getMessage());
		System.err.println("Stack Trace      : ");
		poException.printStackTrace(System.err);
	}
}

// EOF
