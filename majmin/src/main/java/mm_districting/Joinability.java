package mm_districting;

import algorithm.Algorithm;
import algorithm_steps.DummyEdge;
import util.Party;
import util.Race;

import java.util.ArrayList;
import java.util.Set;

public class Joinability {

    public static final double DONE_WITH_MM_THRESHOLD = .3;

    public static double calculateMajMinJoinability(Edge edge) {

        AlgorithmProperties algProps = AlgorithmProperties.getProperties();

        //Doesn't work with florida due to an unknown hang
        if (AlgorithmProperties.getProperties().getState().getName().equalsIgnoreCase("Florida")) {
            return Math.random();
        }

        Cluster clusterOne = edge.getClusterOne();
        Cluster clusterTwo = edge.getClusterOne();

        Set<Precinct> precinctsOne = clusterOne.getPrecincts();
        Set<Precinct> precinctsTwo = clusterTwo.getPrecincts();

        double target = Math.round((algProps.getVotingMajorityThreshold() + algProps.getMinorityVotingThreshold()) / 100.0);

       DemographicContext combined = new DemographicContext();
       DemographicContext contextOne = clusterOne.getDemographicContext();
       DemographicContext contextTwo = clusterTwo.getDemographicContext();

       combined.setAfricanAmerican(contextOne.getAfricanAmerican() + contextTwo.getAfricanAmerican());
       combined.setAmericanIndian(contextOne.getAmericanIndian() + contextTwo.getAmericanIndian());
       combined.setAsian(contextOne.getAsian() + contextTwo.getAsian());
       combined.setPacificIslander(contextOne.getPacificIslander() + contextTwo.getPacificIslander());
       combined.setWhite(contextOne.getWhite() + contextTwo.getWhite());
       combined.setOther(contextOne.getOther() + contextTwo.getOther());
       combined.setTotal(contextOne.getTotal() + contextTwo.getTotal());

       double score = 0;
       double numPrecincts = AlgorithmProperties.getProperties().getState().getPrecincts().size();

       for (Race dem : algProps.getSelectedDemographics()) {
           double combinedRatio = combined.getByRace(dem) * 1.0 / combined.getTotal();
           score += (1 - Math.abs(target - combinedRatio));
       }

        //heavily mm joinability over non mm
        if (score > .2) {
            score += 1;
        }

        double numberOfPrecincts = clusterOne.getPrecincts().size() + clusterTwo.getPrecincts().size();

        double numNeighbors = clusterOne.getNeighbors().size() + clusterTwo.getNeighbors().size();
        double compactness = .01 * (1 - (numNeighbors/numPrecincts));

        score += compactness;

        if (numberOfPrecincts > (numPrecincts * 1.0 / AlgorithmProperties.getProperties().getRequestedNumDistricts() * 1.2)) {
            score = -1;
        }

        return score;
//        return Math.random();
    }

    public static double calculateJoinability(Edge edge) {
        AlgorithmProperties algProps = AlgorithmProperties.getProperties();

        Cluster clusterOne = edge.getClusterOne();
        Cluster clusterTwo = edge.getClusterOne();

        Set<Precinct> precinctsOne = clusterOne.getPrecincts();
        Set<Precinct> precinctsTwo = clusterTwo.getPrecincts();

//        double target = Math.round(((double)algProps.getMajorityVotingThreshold() + (double)algProps.getMinorityVotingThreshold()) / 200.0);

        double score = 0;

        double numPrecincts = AlgorithmProperties.getProperties().getState().getPrecincts().size();
//        double numClusters = AlgorithmProperties.getProperties().getState().getClusters().size();
//        double oneOverC = 1.0 / numClusters;

//        double avgPrecinctRatio = ((numPrecincts * 1.0) / (numClusters * 1.0));

        double numberOfPrecincts = clusterOne.getPrecincts().size() + clusterTwo.getPrecincts().size();

//        double metric = numberOfPrecincts / numPrecincts;

        //now we get score
        //we want to minimize size if possible
        double sizeScore = 1 - Math.abs(numberOfPrecincts / numPrecincts);


        //System.out.println("Size score: "+sizeScore);

//        double demographicScore =  Math.random();

        score = 1 / numberOfPrecincts;
//                score = 1 - Math.abs(metric - oneOverC);

        if (numberOfPrecincts > (numPrecincts * 1.0 / AlgorithmProperties.getProperties().getRequestedNumDistricts() * 1.2)) {
            score = -1;
        }
        return score;
    }

}
