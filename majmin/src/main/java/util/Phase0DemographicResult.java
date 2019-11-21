package util;

import mm_districting.Precinct;

import java.util.Set;

public class Phase0DemographicResult extends Result {
    private Set<Precinct> precincts;

    public Phase0DemographicResult(Set<Precinct> precincts) {
        this.precincts = precincts;
    }

    public Set<Precinct> getPrecincts() {
        return precincts;
    }
}
