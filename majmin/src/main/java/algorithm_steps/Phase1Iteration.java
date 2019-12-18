package algorithm_steps;

import algorithm.Algorithm;
import algorithm.AlgorithmStep;
import algorithm.AlgorithmStepStatus;
import mm_districting.AlgorithmProperties;
import mm_districting.Cluster;
import mm_districting.Precinct;
import mm_districting.State;
import org.javatuples.Pair;
import results.Phase1Result;
import results.Result;

import java.util.ArrayList;

/**
 * Handles the main execution of Phase 1. (Use Case #26, #27, #28, #29) <br>
 * <p>
 * Each iteration assigns new joinabilities and combines clusters.
 *
 * @author Patrick Wamsley
 * @see AssignMMJoinabilities
 * @see AssignJoinabilities
 * @see CombineClusters
 */
public class Phase1Iteration implements AlgorithmStep {

    int i = 0;
    private Algorithm           iteration;
    private boolean             doingMajMin;
    private AlgorithmStepStatus status;

    public Phase1Iteration(boolean doingMajMin) {
        this.doingMajMin = doingMajMin;
        this.status = new AlgorithmStepStatus("Phase1");
    }

    @Override
    public boolean run() {

        State state = AlgorithmProperties.getProperties().getState();

        if (doingMajMin) {
            iteration = new Algorithm(new AssignMMJoinabilities(), new CombineClusters(this));
        }
        else {
            iteration = new Algorithm(new AssignJoinabilities(), new CombineClusters(this));
        }

        while (!iteration.run()) {}

        return AlgorithmProperties.getProperties().getRequestedNumDistricts() >=
               AlgorithmProperties.getProperties().getState().getClusters().size();
    }

    public void doneWithMM() {
        doingMajMin = false;
    }

    @Override
    public AlgorithmStepStatus getStatus() {
        State state = AlgorithmProperties.getProperties().getState();
        status.setProgress(AlgorithmProperties.getProperties().getRequestedNumDistricts() * 1.0f /
                           state.getClusters().size()); //not linear but whatevs
        return status;
    }

    @Override
    public void pause() {}

    @Override
    public Result onCompletion() {
        State state = AlgorithmProperties.getProperties().getState();
        Phase1Result result = new Phase1Result();
        ArrayList<Pair<Cluster,ArrayList<String>>> map = new ArrayList<>();

        for (Cluster c : state.getClusters()) {
            Cluster clone = new Cluster();
            clone.setDemographicContext(c.getDemographicContext());
            clone.setVotingData(c.getVotingData());
            ArrayList<String> geoIds = new ArrayList<>();
            for (Precinct p : c.getPrecincts()) {
                geoIds.add(p.getGeoId());
            }
            map.add(new Pair<>(clone, geoIds));
        }

        result.setMap(map);
        return result;
    }
}
