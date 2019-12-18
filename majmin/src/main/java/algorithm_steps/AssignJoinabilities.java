package algorithm_steps;

import algorithm.AlgorithmStep;
import algorithm.AlgorithmStepStatus;
import mm_districting.*;
import org.hibernate.mapping.Join;
import results.DummyResult;
import results.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class AssignJoinabilities implements AlgorithmStep {

    private State state;
//    private ArrayList<Edge> edgesLeftToAssign;

    private AlgorithmStepStatus status;

    public AssignJoinabilities() {
        this.state = AlgorithmProperties.getProperties().getState();
        this.status = new AlgorithmStepStatus(getClass().getName());
//        this.edgesLeftToAssign = new ArrayList<>();
//        this.edgesLeftToAssign.addAll(state.getEdges());

    }


    @Override
    public boolean run() {

        state.setNextEdgeToCombine(null);

        Edge lastEdge = null;

        for(Cluster c : state.getClusters()){
            for(Cluster n : c.getNeighbors()){

                if (!state.getClusters().contains(n)) {
                    System.out.print("");
                }


                Edge edge;

                //test if in the hash map, if not then calculate and add

                edge = state.getEdgeListing(c, n);
                if(edge == null){
                    edge = new Edge(c, n);
                    edge.setJoinability(Joinability.calculateJoinability(edge));
                    state.setEdgeListing(c, n, edge);
                }

                if (edge.getJoinability() > state.getMaxJoinability(false)) {
                    state.setNextEdgeToCombine(edge);
                }
                lastEdge = edge;
            }
        }

        if(state.getNextEdgeToCombine() == null){
            state.setNextEdgeToCombine(lastEdge);
            if(lastEdge == null){
                System.out.println("ISSUE");
            }
            System.out.println("Used last edge");
            //System.out.println("What again");
        }

        return true;
    }

    @Override
    public AlgorithmStepStatus getStatus() {

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
