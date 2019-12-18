package mm_districting;

import algorithm_steps.DummyEdge;
import util.Party;
import util.Race;

import java.util.ArrayList;
import java.util.Set;

public class Joinability {

    public static final double DONE_WITH_MM_THRESHOLD = .3;

    public static double calculateMajMinJoinability(Edge edge) {

        return Math.random();

//        if (edge instanceof DummyEdge) {
//            return -1;
//        }
//
//        AlgorithmProperties algProps = AlgorithmProperties.getProperties();
//
//        Cluster clusterOne = edge.getClusterOne();
//        Cluster clusterTwo = edge.getClusterOne();
//
//        Set<Precinct> precinctsOne = clusterOne.getPrecincts();
//        Set<Precinct> precinctsTwo = clusterTwo.getPrecincts();
//
//        double target = Math.round((algProps.getVotingMajorityThreshold() + algProps.getMinorityVotingThreshold()) / 100.0);
//
//       DemographicContext combined = new DemographicContext();
//       DemographicContext contextOne = clusterOne.getDemographicContext();
//       DemographicContext contextTwo = clusterTwo.getDemographicContext();
//
//       combined.setAfricanAmerican(contextOne.getAfricanAmerican() + contextTwo.getAfricanAmerican());
//       combined.setAmericanIndian(contextOne.getAmericanIndian() + contextTwo.getAmericanIndian());
//       combined.setAsian(contextOne.getAsian() + contextTwo.getAsian());
//       combined.setPacificIslander(contextOne.getPacificIslander() + contextTwo.getPacificIslander());
//       combined.setWhite(contextOne.getWhite() + contextTwo.getWhite());
//       combined.setOther(contextOne.getOther() + contextTwo.getOther());
//       combined.setTotal(contextOne.getTotal() + contextTwo.getTotal());
//
//       double score = 0;
//
//       for (Race dem : algProps.getSelectedDemographics()) {
//           double combinedRatio = combined.getByRace(dem) * 1.0 / combined.getTotal();
//           score += (1 - Math.abs(target - combinedRatio));
//       }
//
//        //heavily mm joinability over non mm
//        if (score > .2) {
//            score += 1;
//        }
//        return score;
    }

    public static double calculateJoinability(Edge edge) {
        AlgorithmProperties algProps = AlgorithmProperties.getProperties();

        Cluster clusterOne = edge.getClusterOne();
        Cluster clusterTwo = edge.getClusterOne();

        Set<Precinct> precinctsOne = clusterOne.getPrecincts();
        Set<Precinct> precinctsTwo = clusterTwo.getPrecincts();

        double target = Math.round(((double)algProps.getMajorityVotingThreshold() + (double)algProps.getMinorityVotingThreshold()) / 200.0);

        double score = 0;

        double numPrecincts = AlgorithmProperties.getProperties().getState().getPrecincts().size();
        double numClusters = AlgorithmProperties.getProperties().getState().getClusters().size();

        double avgPrecinctRatio = ((numPrecincts * 1.0) / (numClusters * 1.0));

        double numberOfPrecincts = clusterOne.getPrecincts().size() + clusterTwo.getPrecincts().size();

        //now we get score
        //we want to minimize size if possible
        double sizeScore = 1 - Math.abs(numberOfPrecincts / numPrecincts);

        //System.out.println("Size score: "+sizeScore);

        double demographicScore =  Math.random();

        score = sizeScore;
        return score;
    }

}
