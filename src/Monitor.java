import java.util.Arrays;

/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor
{

	boolean[] chopsticks;
	boolean someoneIsTalking = false;


	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// TODO: set appropriate number of chopsticks based on the # of philosophers
		this.chopsticks = new boolean[piNumberOfPhilosophers];
		Arrays.fill(chopsticks, Boolean.TRUE);
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
		// if both chopsticks are available
      int index = piTID-1;
		while (!chopsticks[index] || !chopsticks[piTID%(chopsticks.length - 1)]) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
      chopsticks[index] = false;
      chopsticks[piTID%(chopsticks.length - 1)] = false;
			return;
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
	    int index = piTID-1;
      chopsticks[index] = true;
      chopsticks[piTID%(chopsticks.length - 1)] = true;
      notifyAll();
	}

	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk()
	{
		while (someoneIsTalking) {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
      someoneIsTalking = true;
      return;
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		someoneIsTalking = false;
		notifyAll();
	}
}

// EOF
