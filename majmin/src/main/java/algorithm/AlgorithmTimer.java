package algorithm;
/**
 * A simple Timer to measure the real-time efficiency of an algorithm which can be paused and resumed.
 * Measures in nanoseconds.
 * 
 * @see Algorithm.java
 * @see AlgorithmStep.java
 * 
 * @author Patrick Wamsley
 */
public class AlgorithmTimer {
	
	private long startTime;
	private long runtime = 0;
	private boolean isPaused;
	
	/**
	 * Intalizes a timer, but does not start the clock.
	 */
	public AlgorithmTimer() {
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
	
	public void pause() {
		updateRuntime();
		isPaused = true;
	}
	
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
