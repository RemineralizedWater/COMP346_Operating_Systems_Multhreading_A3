import common.BaseThread;

import java.util.Random;

/**
 * Class Philosopher.
 * Outlines main subroutines of our virtual philosopher.
 *
 * @author Michael Rowe 26101267
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Philosopher extends BaseThread
{
	/**
	 * Max time an action can take (in milliseconds)
	 */
	public static final long TIME_TO_WASTE = 1000;

	/**
	 * The act of eating.
	 * - Print the fact that a given phil (their TID) has started eating.
	 * - yield
	 * - Then sleep() for a random interval.
	 * - yield
	 * - The print that they are done eating.
	 */
	public void eat()
	{
		try
		{
			// Start of implementation of eating, as commented about above
			System.out.println("Philosopher " + this.getTID() + " has started eating.");
			Thread.yield();
			Thread.sleep((long)(Math.random() * TIME_TO_WASTE));
			Thread.yield();
			System.out.println("Philosopher " + this.getTID() + " is done eating.");
			// End of implementation of eating, as commented about above
		}
		catch(InterruptedException e)
		{
			System.err.println("Philosopher.eat():");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	}

	/**
	 * The act of thinking.
	 * - Print the fact that a given phil (their TID) has started thinking.
	 * - yield
	 * - Then sleep() for a random interval.
	 * - yield
	 * - The print that they are done thinking.
	 */
	public void think()
	{
		try
		{
			// Start of implementation of thinking, as commented about above
			System.out.println("Philosopher " + this.getTID() + " has started thinking.");
			Thread.yield();
			Thread.sleep((long)(Math.random() * TIME_TO_WASTE));
			Thread.yield();
			System.out.println("Philosopher " + this.getTID() + " is done thinking.");
			// End of implementation of thinking, as commented about above
		}
		catch(InterruptedException e)
		{
			System.err.println("Philosopher.think():");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	}

	/**
	 * The act of talking.
	 * - Print the fact that a given phil (their TID) has started talking.
	 * - yield
	 * - Say something brilliant at random
	 * - yield
	 * - The print that they are done talking.
	 */
	public void talk()
	{
		// Start of implementation of talking, as commented about above
		System.out.println("Philosopher " + this.getTID() + " has started talking.");
		Thread.yield();
		saySomething();
		Thread.yield();
		System.out.println("Philosopher " + this.getTID() + " is done talking.");
		// End of implementation of talking, as commented about above
	}

	/**
	 * No, this is not the act of running, just the overridden Thread.run()
	 */
	public void run()
	{
		for(int i = 0; i < DiningPhilosophers.DINING_STEPS; i++)
		{
			// Start of monitor for eating
			DiningPhilosophers.soMonitor.pickUp(getTID());

			eat();

			DiningPhilosophers.soMonitor.putDown(getTID());
			// End of monitor for eating

			think();

			/*
			 * A decision is made at random whether this particular
			 * philosopher is about to say something terribly useful.
			 */
			// Randomly chooses a boolean value of true or false, to decide whether to attempt to talk
			boolean decision = new Random().nextBoolean();
			if(decision == true)
			{
				// Start of monitor for talking
				DiningPhilosophers.soMonitor.requestTalk();

				talk();

				DiningPhilosophers.soMonitor.endTalk();
				// End of monitor for talking
			}

			Thread.yield();
		}
	} // run()

	/**
	 * Prints out a phrase from the array of phrases at random.
	 * Feel free to add your own phrases.
	 */
	public void saySomething()
	{
		// Three new deeply philosophical phrases were added...
		String[] astrPhrases =
		{
			"Eh, it's not easy to be a philosopher: eat, think, talk, eat...",
			"You know, true is false and false is true if you think of it",
			"2 + 2 = 5 for extremely large values of 2...",
			"If thee cannot speak, thee must be silent",
			"2 B, or 3 C, that is the sequence",
			"The complexities of \"Yabadabadoo\" are almost historic",
			"Imagine sqrt(-1) has no real solution, it's easy if you try",
			"My number is " + getTID() + ""
		};

		System.out.println
		(
			"Philosopher " + getTID() + " says: " +
			astrPhrases[(int)(Math.random() * astrPhrases.length)]
		);
	}
}

// EOF
