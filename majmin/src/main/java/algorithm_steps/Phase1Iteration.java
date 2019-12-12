package algorithm_steps;

import algorithm.Algorithm;
import algorithm.AlgorithmStep;
import algorithm.AlgorithmStepStatus;
import mm_districting.AlgorithmProperties;
import results.Result;

public class Phase1Iteration implements AlgorithmStep {

    private Algorithm iteration;
    private boolean doingMajMin;

    public Phase1Iteration(boolean doingMajMin) {
        this.doingMajMin = doingMajMin;
    }

    @Override
    public boolean run() {
        if (doingMajMin) {
            iteration = new Algorithm(new AssignMMJoinabilities(), new CombineClusters());
        } else {
            iteration = new Algorithm(new AssignJoinabilities(), new CombineClusters());
        }

        while (!iteration.run()) {}

        return AlgorithmProperties.getProperties().getRequestedNumDistricts() == AlgorithmProperties.getProperties().getNumDistricts();
    }

    public void doneWithMM() {
        doingMajMin = false;
    }

    @Override
    public AlgorithmStepStatus getStatus() {
        return null;
    }

    @Override
    public void pause() {

    }

    @Override
    public Result onCompletion() {
        return null;
    }
}
