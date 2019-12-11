package mm_districting;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a district object, containing geographic data as well as voting and demographic data.
 *
 * @author Patrick Wamsley
 * @author Brett Weinger
 * @author Felix Rieg-Baumhauer
 * @author Niko Ziozis
 */
@Entity
@Table(name = "DISTRICT")
@JsonIgnoreProperties(value = { "precincts" })
public class District {

    private Set<Precinct> precincts;

    private DemographicContext demographics;
    private Voting             votingSet;

    // String of the GeoJSON data
    private String geography;
    private long   geoId;
    private int    districtNumber;

    public District() {

        precincts = new HashSet<>();
    }

    public District(String geography, long geoId, int districtNumber) {
        this.geography = geography;
        this.districtNumber = districtNumber;
        this.geoId = geoId;
        this.precincts = new HashSet<>();
        this.demographics = new DemographicContext(0, 0, 0, 0, 0, 0, 0);
    }

    /**
     * @return The given Cluster converted to a District object.
     */
    public static District fromCluster(Cluster cluster) {
        District d = new District();
        d.precincts = cluster.getPrecincts();
        return d;
    }

    @Id
    @Column(name = "GEO_ID")
    public long getGeoId() {
        return this.geoId;
    }

    public void setGeoId(long geoId) {
        this.geoId = geoId;
    }

    public boolean addPrecinct(Precinct p) {
        return precincts.add(p);
    }

    public boolean removePrecinct(Precinct p) {
        return precincts.remove(p);
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "DISTRICT_TO_PRECINCT")
    @JoinColumn(name = "PRECINCT_ID")
    public Set<Precinct> getPrecincts() {
        return precincts;
    }

    public void setPrecincts(Set<Precinct> precincts) {
        this.precincts = precincts;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "DISTRICT_TO_DEMOGRAPHIC")
    @JoinColumn(name = "CONTEXT_ID")
    public DemographicContext getDemographics() {
        return demographics;
    }

    public void setDemographics(DemographicContext demographics) {
        this.demographics = demographics;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "DISTRICT_TO_VOTING")
    @JoinColumn(name = "VOTING_DATA_ID")
    public Voting getVotingSet() {
        return votingSet;
    }

    public void setVotingSet(Voting votingData) {
        this.votingSet = votingData;
    }

    @Column(name = "GEOGRAPHY", columnDefinition = "longtext", nullable = false)
    public String getGeography() {
        return geography;
    }

    public void setGeography(String geography) {
        this.geography = geography;
    }

    @Column(name = "DISTRICT_NUMBER", nullable = false)
    public int getDistrictNumber() {
        return districtNumber;
    }

    public void setDistrictNumber(int districtNumber) {
        this.districtNumber = districtNumber;
    }
}
