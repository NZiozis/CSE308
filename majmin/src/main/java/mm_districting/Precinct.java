package mm_districting;

import util.Election;
import util.NoSuchElectionException;
import util.Party;
import util.Race;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Precinct, which are combined to create Voting districts.
 * <p>
 * Contains geographic data as well as voting and demographic bloc data.
 *
 * @author Patrick Wamsley
 * @author Brett Weinger
 * @author Felix Rieg-Baumhauer
 * @author Niko Ziozis
 */
@Entity
@Table(name = "PRECINCT")
public class Precinct {

    private String county;
    private String geoId;

    private DemographicContext demographics;
    private Set<Voting>        votingSet;
    private Race               demographicBloc;

    @Transient
    private Party              votingBloc;

    private Party              partyBloc;
    private String             geography;

    //    private Precinct      precinct;
    private Set<Precinct> neighbor;

    public Precinct() {}

    public Precinct(String county, String geoId) {
        this.county = county;
        this.geoId = geoId;
    }

    public Precinct(String county, String geoId, DemographicContext demographics, String geography) {
        this.county = county;
        this.geoId = geoId;
        this.demographics = demographics;
        this.votingSet = new HashSet<>();
        this.neighbor = new HashSet<>();
        this.geography = geography;
    }

    public double getDemographicPercent(Race race) {
        return 0;
    }

    /**
     * @return The set of {@code Voting} objects for the given election
     */
    public Set<Voting> getElectionResult(Election election) {
        Set<Voting> results = new HashSet<>();
        for (Voting voting : votingSet) {
            if (voting.getElection() == election) {
                results.add(voting);
            }
        }

        if (results.isEmpty()) {
            throw new NoSuchElectionException();
        }

        return results;
    }

    @Column(name = "COUNTY", nullable = false)
    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Id
    @Column(name = "ID", length = 30)
    public String getGeoId() {
        return geoId;
    }

    public void setGeoId(String geoId) {
        this.geoId = geoId;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "PRECINCT_TO_DEMOGRAPHIC")
    @JoinColumn(name = "CONTEXT_ID")
    public DemographicContext getDemographics() {
        return demographics;
    }

    public void setDemographics(DemographicContext demographics) {
        this.demographics = demographics;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "PRECINCT_TO_VOTING")
    @JoinColumn(name = "VOTING_DATA_ID")
    public Set<Voting> getVotingSet() {
        return votingSet;
    }

    public void setVotingSet(Set<Voting> votingSet) {
        this.votingSet = votingSet;
    }

    @Transient
    public Race getDemographicBloc() {
        return demographicBloc;
    }

    public void setDemographicBloc(Race demographicBloc) {
        this.demographicBloc = demographicBloc;
    }

    @Transient
    public Party getPartyBloc() {
        return partyBloc;
    }

    public void setPartyBloc(Party partyBloc) {
        this.partyBloc = partyBloc;
    }

    @Column(name = "GEOGRAPHY", columnDefinition = "mediumtext", nullable = false)
    public String getGeography() {
        return geography;
    }

    public void setGeography(String geography) {
        this.geography = geography;
    }

    @OneToMany(targetEntity = Precinct.class)
    @JoinTable(name = "PRECINCT_NEIGHBORS")
    @JoinColumns( { @JoinColumn(name = "PRECINCT_ID", referencedColumnName = "ID"),
                    @JoinColumn(name = "NEIGHBOR_ID", referencedColumnName = "ID", unique = false) })
    public Set<Precinct> getNeighbor() {
        return neighbor;
    }

    public void setNeighbor(Set<Precinct> neighbor) {
        this.neighbor = neighbor;
    }

    public boolean addNeighbor(Precinct precinct) {
        return this.neighbor.add(precinct);
    }

    public boolean addVotingData(Voting voting) {
        return this.votingSet.add(voting);
    }

    @Transient
    public Party getVotingBloc() {
        return votingBloc;
    }

    public void setVotingBloc(Party votingBloc) {
        this.votingBloc = votingBloc;
    }
}
