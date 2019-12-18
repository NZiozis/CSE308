package algorithm_steps;


import algorithm.Algorithm;
import algorithm.AlgorithmStep;
import algorithm.AlgorithmStepStatus;
import mm_districting.*;
import results.DummyResult;
import results.Result;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Generates the initial {@code Set} of {@code Cluster} objects for phase1 execution,
 * and assigns the current {@code State} object's Cluster set. <br>
 *
 * Each cluster begins as one precinct.
 * @author Patrick Wamsley
 */
public class GenerateInitialClusters implements AlgorithmStep {

    private Set<Cluster> clusters;

    public GenerateInitialClusters() {
        clusters = new HashSet<>();
    }

    @Override
    public boolean run() {
        State state = AlgorithmProperties.getProperties().getState();
        for (Precinct precinct : state.getPrecincts()) {
            Cluster cluster = new Cluster();
            cluster.addPrecinct(precinct);
            clusters.add(cluster);
            state.addClusterPrecinctMapping(precinct, cluster);
            cluster.setVotingData(precinct.getVotingSet());
            cluster.setDemographicContext(precinct.getDemographics());
        }
        state.setClusters(clusters);

        HashMap<Cluster, HashMap<Cluster, Edge>> edgeHash = state.getEdgeHash();
        //now we have the clusters and we make the edge set
        for (Cluster c : clusters){
            HashMap<Cluster, Edge> tempHash = new HashMap<Cluster, Edge>();
            edgeHash.put(c, tempHash);
        }

        return true;
    }

    @Override
    public AlgorithmStepStatus getStatus() {
        AlgorithmStepStatus status = new AlgorithmStepStatus("GenerateInitialClusters");
        status.setProgress(1);
        return status;
    }

    @Override
    public void pause() {}

    @Override
    public Result onCompletion() {
        return new DummyResult();
    }
}
