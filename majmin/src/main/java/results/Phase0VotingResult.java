package results;

import mm_districting.Precinct;

import java.util.Set;

/**
 * Result of {@code DetermineVotingBlocs} phase0 algorithm step. <br>
 *
 * Holds the set of precincts containing a voting bloc.
 *
 * @author Patrick Wamsley
 */
public class Phase0VotingResult extends Phase0DemographicResult {

    public Phase0VotingResult(Set<Precinct> precincts) {
        super(precincts);
    }
}
