package algorithm;
/**
 * Represents the current status of a running AlgorithmStep 
 * @see {@code AlgorithmStep.java}
 * 
 * @author Patrick Wamsley
 */
public class AlgorithmStepStatus {

	/**
	 * The current progress of this step in the algorithm. A {@code progress} value of 1 represents a completed step.
	 */
	private float progress;
	
	/**
	 * A String status update which represents the current status of the AlgorithmStep. 
	 */
	private String message;
	
	/**
	 * The name of the AlgorithmStep
	 */
	private String algorithmStepName;  
	
	public AlgorithmStepStatus(String algorithmStepName) {
		this.algorithmStepName = algorithmStepName;
	}

	public float getProgress() {
		return progress;
	}

	public String getMessage() {
		return message;
	}

	public void setProgress(float progress) {
		
		if (progress < 0 || progress > 1) {
			System.err.println("WARNING: tried to set a nonsensical progress value to an AlgorithmStep. This request is being ignored.");
			return;
		}
		
		this.progress = progress;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return algorithmStepName + ": progress = " + progress + ". Current status: " + message;
	}

	public String getAlgorithmStepName() {
		return algorithmStepName;
	}
}