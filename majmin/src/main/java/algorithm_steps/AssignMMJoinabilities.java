package algorithm_steps;

import algorithm.Algorithm;
import algorithm.AlgorithmStep;
import algorithm.AlgorithmStepStatus;
import mm_districting.*;
import results.DummyResult;
import results.Result;

import java.util.ArrayList;

/**
 * Assigns a joinability value for each edge based on Majority-Minority considerations (use case #26).
 * @author Patrick Wamsley
 */
//TODO: decide between majminjoinability and joinability or combine
public class AssignMMJoinabilities implements AlgorithmStep {

    private State state;
    private ArrayList<Edge> edgesLeftToAssign;

    private AlgorithmStepStatus status;

    public AssignMMJoinabilities() {
        this.state = AlgorithmProperties.getProperties().getState();
        this.status = new AlgorithmStepStatus(getClass().getName());
        this.edgesLeftToAssign = new ArrayList<>();
        this.edgesLeftToAssign.addAll(state.getEdges());
    }

    /**
     * Runs one iteration of this step by assigning a majority-minority joinability value to an edge.
     *
     * @return true when this step has completed, false otherwise
     */
    @Override
    public boolean run() {
        status.setMessage("Currently running.");
        Edge edge = edgesLeftToAssign.get(0);

        Cluster c1 = edge.getClusterOne();
        Cluster c2 = edge.getClusterTwo();

        if (c1.isCombinedCluster() || c2.isCombinedCluster()) {
            boolean c1In = state.getClusters().contains(c1);
            boolean c2In = state.getClusters().contains(c2);
            System.out.print("");
        }

        //skip edges with clusters not in play
        boolean validEdge = state.getClusters().contains(edge.getClusterOne()) && state.getClusters().contains(edge.getClusterTwo());
        if (!validEdge) {
            boolean c1In = state.getClusters().contains(c1);
            boolean c2In = state.getClusters().contains(c2);

            if (!c1In) {
                for (Edge e : state.getEdges()) {
                    if (!e.equals(edge) && e.hasClusters(c1)) {
                        System.out.print("");
                    }
                }
            }

            if (!c2In) {
                for (Edge e : state.getEdges()) {
                    if (!e.equals(edge) && e.hasClusters(c2)) {
                        System.out.print("");
                    }
                }
            }

            edge.setMajorityMinorityJoinability(-1);
            edgesLeftToAssign.remove(edge);
            return edgesLeftToAssign.isEmpty();
        }

        edge.setMajorityMinorityJoinability(Joinability.calculateMajMinJoinability(edge));
        edgesLeftToAssign.remove(edge);

        double clusterOneMaxJoinability = state.getMaxJoinability(edge.getClusterOne());
        double clusterTwoMaxJoinability = state.getMaxJoinability(edge.getClusterTwo());



        if (edge.getMajMinJoinability() > clusterOneMaxJoinability) {
            state.updateMostJoinable(edge.getClusterOne(), edge);
        }

        if (edge.getMajMinJoinability() > clusterTwoMaxJoinability) {
            state.updateMostJoinable(edge.getClusterTwo(), edge);
        }
        return edgesLeftToAssign.isEmpty();
    }

    @Override
    public AlgorithmStepStatus getStatus() {
        status.setProgress(edgesLeftToAssign.size() * 1.0f / state.getEdges().size());
        if (Double.compare(status.getProgress(), 1.0) == 0) {
            status.setMessage("Completed.");
        }
        return status;
    }

    @Override
    public void pause() {
        status.setMessage("Currently paused.");
    }

    @Override
    public Result onCompletion() {
        return new DummyResult();
    }
}
