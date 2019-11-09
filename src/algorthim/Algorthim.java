package algorthim;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Modularly implements an Algorthim composed of {@code AlgorthimStep}s, which allows for pausing and status updates.
 * 
 * @author Patrick Wamsley
 */
public class Algorthim implements AlgorthimStep {
	
	private ArrayList<AlgorthimStep> steps = new ArrayList<>(); 
	private int currentStepIndex = 0;
	
	private AlgorthimStep currentStep;
	
	private boolean isPaused = true;
	
	public Algorthim(AlgorthimStep... steps) {
		this.steps.addAll(Arrays.asList(steps));
		this.currentStep = this.steps.get(0);
	}

	@Override
	public boolean run() {
		if (currentStep != null && !isPaused && currentStep.run()) {
			if (currentStepIndex - 1 == steps.size()) {
				return true;
			}
			currentStep = steps.get(currentStepIndex++);
		}
		
		return false;
	}

	@Override
	public AlgorthimStepStatus getStatus() {
		AlgorthimStepStatus status = new AlgorthimStepStatus("Main Algorthim: currently on step: " + currentStep.getStatus().getAlgorthimStepName());
		status.setMessage(currentStep.getStatus().getMessage());
		status.setProgress((currentStepIndex * 1.0f / steps.size()) + (currentStep.getStatus().getProgress() / steps.size()));
		return status;
	}

	@Override
	public void pause() {
		isPaused = true;
		currentStep.pause();
	}
	
	public void resume() {
		isPaused = false;
	}

}
