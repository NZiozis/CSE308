package mm_districting;

import util.Party;
import util.Race;

import java.util.ArrayList;
import java.util.Set;

public class Joinability {

    public static final double DONE_WITH_MM_THRESHOLD = .3;

    public static double calculateMajMinJoinability(Edge edge) {

        AlgorithmProperties algProps = AlgorithmProperties.getProperties();

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

       for (Race dem : algProps.getSelectedDemographics()) {
           double combinedRatio = combined.getByRace(dem) * 1.0 / combined.getTotal();
           score += (1 - Math.abs(target - combinedRatio));
       }

        //heavily mm joinability over non mm
        if (score > .2) {
            score += 1;
        }
        return score;
    }

    public static double calculateJoinability(Edge edge) {
        return Math.random();
    }

}
