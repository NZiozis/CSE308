package algorithm_steps;

import algorithm.Algorithm;
import algorithm.AlgorithmStep;
import algorithm.AlgorithmStepStatus;
import mm_districting.AlgorithmProperties;
import mm_districting.Cluster;
import mm_districting.Edge;
import mm_districting.State;
import results.DummyResult;
import results.Result;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Combines previously identified best-pairing Clusters
 *
 * @author Patrick Wamsley
 */
public class CombineClusters implements AlgorithmStep {

    /**
     * Used to prevent too many iterations in this iteration, as updated Joinabilities may lead to better pairings.
     */
    private int maxRunIterations;

    private int iterations;

    private Iterator<Cluster> iterator;

    public CombineClusters() {
        this(AlgorithmProperties.getProperties().getState().getClusters().size());
    }

    public CombineClusters(int maxRunIterations) {
        this.maxRunIterations = maxRunIterations;
        this.iterations = 0;
        iterator = AlgorithmProperties.getProperties().getState()
                    .getBestPairings().keySet().iterator();
    }

    @Override
    public boolean run() {
        State state = AlgorithmProperties.getProperties().getState();
        Edge edge = state.getMostJoinableEdge(iterator.next());
        Set<Cluster> doNotPairClusters = state.getDoNotPairClusters();

        iterations++;

        //skip edges with a cluster in the do-not-pair set
        if (doNotPairClusters.contains(edge.getClusterOne()) || doNotPairClusters.contains(edge.getClusterTwo())) {
            return false;
        }

        state.combineClusters(edge);

        return (!iterator.hasNext()) || iterations >= maxRunIterations;
    }

    private static Cluster getOtherCluster(Edge edge, Cluster cluster) {
        return edge.getClusterOne().equals(cluster) ? edge.getClusterTwo() : edge.getClusterOne();
    }

    @Override
    public AlgorithmStepStatus getStatus() {
        AlgorithmStepStatus status = new AlgorithmStepStatus("CombineClusters");
        State state = AlgorithmProperties.getProperties().getState();
        status.setProgress(Math.max(iterations * 1.0f / maxRunIterations, iterations * 1.0f / state.getClusters().size()));
        return status;
    }

    @Override
    public void pause() {}

    @Override
    public Result onCompletion() {
        AlgorithmProperties.getProperties().getState().getDoNotPairClusters().clear();
        return new DummyResult(); //TODO: probably return an actual result
    }
}
