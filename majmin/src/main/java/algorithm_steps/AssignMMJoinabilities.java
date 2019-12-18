package algorithm_steps;

import algorithm.Algorithm;
import algorithm.AlgorithmStep;
import algorithm.AlgorithmStepStatus;
import mm_districting.*;
import results.DummyResult;
import results.Result;

import java.util.ArrayList;
import java.util.Set;

/**
 * Assigns a joinability value for each edge based on Majority-Minority considerations (use case #26).
 * @author Patrick Wamsley
 */
//TODO: decide between majminjoinability and joinability or combine
public class AssignMMJoinabilities implements AlgorithmStep {

    private State state;
//    private ArrayList<Edge> edgesLeftToAssign;

    private AlgorithmStepStatus status;

    public AssignMMJoinabilities() {
        this.state = AlgorithmProperties.getProperties().getState();
        this.status = new AlgorithmStepStatus(getClass().getName());
//        this.edgesLeftToAssign = new ArrayList<>();
//        this.edgesLeftToAssign.addAll(state.getEdges());
    }

    /**
     * Runs one iteration of this step by assigning a majority-minority joinability value to an edge.
     *
     * @return true when this step has completed, false otherwise
     */
    @Override
    public boolean run() {

        state.setNextEdgeToCombine(null);

        for(Cluster c : state.getClusters()){
            for(Cluster n : c.getNeighbors()){

                if (!state.getClusters().contains(n)) {
                    System.out.print("");
                }

                Edge edge = new Edge(c, n);
                edge.setMajorityMinorityJoinability(Joinability.calculateMajMinJoinability(edge));
                if(edge.getMajMinJoinability() > state.getMaxJoinability(true)){
                    state.setNextEdgeToCombine(edge);
                }
            }
        }

        return true;
    }

    @Override
    public AlgorithmStepStatus getStatus() {

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
