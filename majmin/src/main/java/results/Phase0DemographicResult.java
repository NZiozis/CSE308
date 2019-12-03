package results;

import mm_districting.Precinct;

import java.util.Set;

/**
 * Result of {@code DetermineDemBlocs} phase0 algorithm step. <br>
 *
 * Holds the set of precincts containing a demographic bloc.
 *
 * @author Patrick Wamsley
 */
public class Phase0DemographicResult extends Result {

    private Set<Precinct> precincts;

    public Phase0DemographicResult(Set<Precinct> precincts) {
        this.precincts = precincts;
    }

    public Set<Precinct> getPrecincts() {
        return precincts;
    }
}
