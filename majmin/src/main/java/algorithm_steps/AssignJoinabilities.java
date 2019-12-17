package algorithm_steps;

import algorithm.AlgorithmStep;
import algorithm.AlgorithmStepStatus;
import mm_districting.AlgorithmProperties;
import mm_districting.Edge;
import mm_districting.Joinability;
import mm_districting.State;
import results.DummyResult;
import results.Result;

import java.util.ArrayList;

public class AssignJoinabilities implements AlgorithmStep {

    private State state;
    private ArrayList<Edge> edgesLeftToAssign;

    private AlgorithmStepStatus status;

    public AssignJoinabilities() {
        this.state = AlgorithmProperties.getProperties().getState();
        this.status = new AlgorithmStepStatus(getClass().getName());
        this.edgesLeftToAssign = new ArrayList<>();
        this.edgesLeftToAssign.addAll(state.getEdges());
    }


    @Override
    public boolean run() {
        status.setMessage("Currently running.");
        Edge edge = edgesLeftToAssign.get(0);

        //skip edges with clusters not in play
        boolean validEdge = state.getClusters().contains(edge.getClusterOne()) && state.getClusters().contains(edge.getClusterTwo());
        if (!validEdge) {
            edge.setJoinability(-1);
            edgesLeftToAssign.remove(edge);
            return edgesLeftToAssign.isEmpty();
        }

        edge.setJoinability(Joinability.calculateJoinability(edge));
        edgesLeftToAssign.remove(edge);

        double clusterOneMaxJoinability = state.getMaxJoinability(edge.getClusterOne());
        double clusterTwoMaxJoinability = state.getMaxJoinability(edge.getClusterTwo());

        if (edge.getJoinability() > clusterOneMaxJoinability) {
            state.updateMostJoinable(edge.getClusterOne(), edge);
        }

        if (edge.getJoinability() > clusterTwoMaxJoinability) {
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

    }

    @Override
    public Result onCompletion() {
        return new DummyResult();
    }
}
