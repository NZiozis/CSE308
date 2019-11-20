package mm_districting;

import util.DemographicContextConverter;
import util.Party;
import util.Race;

import javax.persistence.*;
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
    private Party              partyBloc;

    private Set<Precinct> neighbors;


    public Precinct(String county, String geoId) {
        this.county = county;
        this.geoId = geoId;
    }

    //TODO Figure out how to map to states and districts.

    public double getDemographicPercent(Race race) {
        return 0;
    }

    @Column(name = "COUNTY", nullable = false)
    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Id
    @Column(name = "GEO_ID")
    public String getGeoId() {
        return geoId;
    }

    public void setGeoId(String geoId) {
        this.geoId = geoId;
    }

    @Convert(converter = DemographicContextConverter.class)
    public DemographicContext getDemographics() {
        return demographics;
    }

    public void setDemographics(DemographicContext demographics) {
        this.demographics = demographics;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "DATA_ID")
    public Set<Voting> getVotingSet() {
        return votingSet;
    }

    public void setVotingSet(Set<Voting> votingSet) {
        this.votingSet = votingSet;
    }

    public Race getDemographicBloc() {
        return demographicBloc;
    }

    public void setDemographicBloc(Race demographicBloc) {
        this.demographicBloc = demographicBloc;
    }

    public Party getPartyBloc() {
        return partyBloc;
    }

    public void setPartyBloc(Party partyBloc) {
        this.partyBloc = partyBloc;
    }

    //TODO Determine how the annotations should work here
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "PRECINCT_ID")
    public Set<Precinct> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(Set<Precinct> neighbors) {
        this.neighbors = neighbors;
    }


}
