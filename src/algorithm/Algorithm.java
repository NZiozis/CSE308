package algorithm;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Modularly implements an Algorithm composed of {@code AlgorithmStep}s, which allows for pausing and status updates.
 * 
 * @author Patrick Wamsley
 */
public class Algorithm implements AlgorithmStep {
	
	private ArrayList<AlgorithmStep> steps = new ArrayList<>(); 
	private int currentStepIndex = 0;
	
	private AlgorithmStep currentStep;
	
	private boolean isPaused = true;
	
	public Algorithm(AlgorithmStep... steps) {
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
	public AlgorithmStepStatus getStatus() {
		AlgorithmStepStatus status = new AlgorithmStepStatus("Main Algorithm: currently on step: " + currentStep.getStatus().getAlgorithmStepName());
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
