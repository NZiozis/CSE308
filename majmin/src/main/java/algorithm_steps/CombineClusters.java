package algorithm_steps;

import algorithm.Algorithm;
import algorithm.AlgorithmStep;
import algorithm.AlgorithmStepStatus;
import mm_districting.*;
import results.DummyResult;
import results.Result;

import java.util.*;

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
    private ArrayList<Edge> queuedCombinations;

    private boolean firstRun;
    private double highestSeenJoinability = -1;

    private Phase1Iteration caller;

    public CombineClusters(Phase1Iteration caller) {
        this(AlgorithmProperties.getProperties().getState().getClusters().size());
        this.caller = caller;
    }

    public CombineClusters(int maxRunIterations) {
        this.maxRunIterations = maxRunIterations;
        this.iterations = 0;
        this.firstRun = true;
        this.queuedCombinations = new ArrayList<>();
    }

    @Override
    public boolean run() {

        if (firstRun) {
            iterator = AlgorithmProperties.getProperties().getState()
                    .getBestPairings().keySet().iterator();
            firstRun = false;
        }

        if (!iterator.hasNext()) {
            return true;
        }

        State state = AlgorithmProperties.getProperties().getState();
        Edge edge = state.getMostJoinableEdge(iterator.next());
        Set<Cluster> doNotPairClusters = state.getDoNotPairClusters();

        iterations++;

        if (edge instanceof DummyEdge) {
            return (!iterator.hasNext()) || iterations >= maxRunIterations;
        }

        //skip edges with a cluster in the do-not-pair set
        if (doNotPairClusters.contains(edge.getClusterOne()) || doNotPairClusters.contains(edge.getClusterTwo())) {
            return false;
        } else {
            if (edge.getMajMinJoinability() > highestSeenJoinability) {
                highestSeenJoinability = edge.getMajMinJoinability();
            }

            queuedCombinations.add(edge);
            doNotPairClusters.add(edge.getClusterOne());
            doNotPairClusters.add(edge.getClusterTwo());

            for (Edge e : state.getEdges()) {
                if (e.hasClusters(edge.getClusterOne(), edge.getClusterTwo())) {
                    doNotPairClusters.add(e.getClusterOne());
                    doNotPairClusters.add(e.getClusterTwo());
                }
            }

            return (!iterator.hasNext()) || iterations >= maxRunIterations;
        }
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
        State state = AlgorithmProperties.getProperties().getState();
        for (Edge e : queuedCombinations) {
            state.combineClusters(e);
        }
        state.getDoNotPairClusters().clear();

        if (highestSeenJoinability < Joinability.DONE_WITH_MM_THRESHOLD) {
            caller.doneWithMM();
        }

        return new DummyResult(); //TODO: probably return an actual result
    }
}
