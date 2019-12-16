package algorithm_steps;

import algorithm.AlgorithmStep;
import algorithm.AlgorithmStepStatus;
import mm_districting.*;
import results.DummyResult;
import results.Result;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Generates the initial {@code Set} of {@code Edge} objects for phase1 execution,
 * and assigns the current {@code State} object's Edge set.
 * @author Patrick Wamsley
 */
public class GenerateInitialEdges implements AlgorithmStep {

    private Set<Edge> edges;

    public GenerateInitialEdges() {
        edges = new HashSet<>();
    }

    @Override
    public boolean run() {
        State state = AlgorithmProperties.getProperties().getState();
        Map<String, Cluster> clusterMap = state.getInitialClustersMap();

        int i = 0;
        for (Cluster cluster : state.getClusters()) {
            Precinct initialPrecinct = cluster.getPrecincts().iterator().next();
            for (Precinct neighbor : initialPrecinct.getNeighbor()) {
                Cluster neighborCluster = clusterMap.get(neighbor.getGeoId());
                Edge edge = new Edge(cluster, clusterMap.get(neighbor.getGeoId()));
                cluster.addEdge(edge);
                neighborCluster.addEdge(edge);
                edges.add(edge);
            }

        }
        state.setEdges(edges);
        return true;
    }

    @Override
    public AlgorithmStepStatus getStatus() {
        return null;
    }

    @Override
    public void pause() {}

    @Override
    public Result onCompletion() {
        return new DummyResult();
    }
}
