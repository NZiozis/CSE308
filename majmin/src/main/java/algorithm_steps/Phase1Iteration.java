package algorithm_steps;

import algorithm.Algorithm;
import algorithm.AlgorithmStep;
import algorithm.AlgorithmStepStatus;
import mm_districting.*;
import results.Phase1Result;
import results.Result;

import java.util.HashMap;

/**
 * Handles the main execution of Phase 1. (Use Case #26, #27, #28, #29) <br>
 *
 * Each iteration assigns new joinabilities and combines clusters.
 *
 * @see AssignMMJoinabilities
 * @see AssignJoinabilities
 * @see CombineClusters
 *
 * @author Patrick Wamsley
 */
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
    public void pause() {}

    @Override
    public Result onCompletion() {
        State state = AlgorithmProperties.getProperties().getState();
        Phase1Result result = new Phase1Result();

        HashMap<Cluster, String[]> map = new HashMap<>();
        for (Cluster cluster : state.getClusters()) {
            String[] geoIds = new String[cluster.getPrecincts().size()];
            int i = 0;
            for (Precinct precinct : cluster.getPrecincts()) {
                geoIds[i++] = precinct.getGeoId();
            }
            map.put(cluster, geoIds);
        }

        result.setMap(map);
        return result;
    }
}
