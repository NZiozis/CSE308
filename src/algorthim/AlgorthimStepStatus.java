package algorthim;
/**
 * Represents the current status of a running AlgorthimStep 
 * @see {@code AlgorthimStep.java}
 * 
 * @author Patrick Wamsley
 */
public class AlgorthimStepStatus {

	/**
	 * The current progress of this step in the algorthim. A {@code progress} value of 1 represents a completed step.
	 */
	private float progress;
	
	/**
	 * A String status update which represents the current status of the AlgorthimStep. 
	 */
	private String message;
	
	/**
	 * The name of the AlgorthimStep
	 */
	private String algorthimStepName;  
	
	public AlgorthimStepStatus(String algorthimStepName) {
		this.algorthimStepName = algorthimStepName;
	}

	public float getProgress() {
		return progress;
	}

	public String getMessage() {
		return message;
	}

	public void setProgress(float progress) {
		
		if (progress < 0 || progress > 1) {
			System.err.println("WARNING: tried to set a nonsensical progress value to an AlgorthimStep. This request is being ignored.");
			return;
		}
		
		this.progress = progress;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return algorthimStepName + ": progress = " + progress + ". Current status: " + message;
	}

	public String getAlgorthimStepName() {
		return algorthimStepName;
	}
}