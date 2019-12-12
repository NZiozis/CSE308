package algorithm;
import results.DummyResult;
import results.Result;

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

	private ArrayList<Result> resultsToSend;
	
	public Algorithm(AlgorithmStep... steps) {
		this.steps.addAll(Arrays.asList(steps));
		this.currentStep = this.steps.get(0);
		resultsToSend = new ArrayList<>();
	}

	public ArrayList<Result> getResultsToSend() {
		return resultsToSend;
	}

	/**
	 * Runs one iteration of the current algorithm step's run(). 
	 * 
	 * @return true on completion of the algorithm, false otherwise
	 */
	@Override
	public boolean run() {
		if (currentStep != null && currentStep.run()) {
			Result lastStepResult = currentStep.onCompletion();
			if (!(lastStepResult instanceof DummyResult)) {
				resultsToSend.add(lastStepResult);
			}
			if (currentStepIndex - 1 == steps.size()) {
				return true;
			}
			currentStep = steps.get(++currentStepIndex);
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

	@Override
	public Result onCompletion() {
		return null;
	}

	public void resume() {
		isPaused = false;
	}

}
