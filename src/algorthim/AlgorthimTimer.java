package algorthim;
/**
 * A simple Timer to measure the real-time efficiency of an algorthim which can be paused and resumed.
 * Measures in nanoseconds.
 * 
 * @author Patrick Wamsley
 */
public class AlgorthimTimer {
	
	private long startTime;
	private long runtime = 0;
	private boolean isPaused;
	
	/**
	 * Intalizes a timer, but does not start the clock.
	 */
	public AlgorthimTimer() {
		isPaused = true;
	}
	
	/**
	 * Starts or resumes running this timer's clock. 
	 */
	public void run() {
		
		if (!isPaused) {
			System.err.println("Warning: run() was called on an already-running timer");
		}
		
		startTime = System.nanoTime();
		isPaused = false;
	}
	
	/**
	 * Pauses the timer.
	 */
	public void pause() {
		updateRuntime();
		isPaused = true;
	}
	
	/**
	 * @return The timer's runtime. 
	 */
	public long getRuntime() {
		
		if (!isPaused) {
			updateRuntime();
		} 

		return runtime;
	}
	
	private void updateRuntime() {
		long currentTime = System.nanoTime();
		runtime += currentTime - startTime; 
		startTime = currentTime; 
	}

}
