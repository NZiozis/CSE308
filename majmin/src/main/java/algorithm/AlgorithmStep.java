package algorithm;

/**
 * Interface for building an {@code Algorithm} using AlgorithmSteps to increase modularity and to cleanly handle interupts (pauses). 
 * 
 * @see Algorithm.java
 * @author Patrick Wamsley
 */
public interface AlgorithmStep {

	/**
	 * Runs this step in the Algorithm. 
	 * 
	 * @return True on completion of this step of the algorithm, false otherwise
	 */
	public boolean run();

	/**
	 * @return The current status of this step in the Algorithm. {@code AlgorithmStepStatus} objects contain a {@code progress} percentage and a String message.
	 */
	public AlgorithmStepStatus getStatus();

	/**
	 * Called when the execution of this AlgorithmStep is paused. 
	 */
	public void pause();

	/**
	 * Called once when this algorithm step finishes
	 */
	public void onCompletition() {

	}
}
