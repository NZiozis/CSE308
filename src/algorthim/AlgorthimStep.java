package algorthim;

/**
 * Interface for building an algorthim using AlgorthimSteps to increase modularity and to cleanly handle interupts (pauses). 
 * 
 * @author Patrick Wamsley
 */
public interface AlgorthimStep {

	/**
	 * Runs this step in the Algorthim. 
	 * 
	 * @return True on completion, False if still running
	 */
	public boolean run();

	/**
	 * @return The current status of this step in the alogrthim. {@code AlgorthimStepStatus} objects contain a {@code progress} percentage and a String message.
	 */
	public AlgorthimStepStatus getStatus();

	/**
	 * Pauses the execution of this AlgorthimStep. 
	 */
	public void pause();
}
