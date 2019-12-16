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

        //skip edges with a cluster in the do-not-pair set
        if (doNotPairClusters.contains(edge.getClusterOne()) || doNotPairClusters.contains(edge.getClusterTwo())) {
            return false;
        } else {
            if (edge.getJoinability() > highestSeenJoinability) {
                highestSeenJoinability = edge.getJoinability();
            }
            queuedCombinations.add(edge);
            state.getDoNotPairClusters().add(edge.getClusterOne());
            state.getDoNotPairClusters().add(edge.getClusterTwo());
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

        ArrayList<Cluster> buggedClusters = new ArrayList<>();

        for (Edge e : queuedCombinations) {
            boolean x = state.getClusters().contains(e.getClusterOne());
            boolean y = state.getClusters().contains(e.getClusterTwo());
            if (!x) {
                buggedClusters.add(e.getClusterOne());
                System.out.println();
            } if (!y) {
                buggedClusters.add(e.getClusterTwo());
            }
            state.combineClusters(e);
        }
        state.getDoNotPairClusters().clear();

        if (highestSeenJoinability < Joinability.DONE_WITH_MM_THRESHOLD) {
            caller.doneWithMM();
        }

        return new DummyResult(); //TODO: probably return an actual result
    }
}
