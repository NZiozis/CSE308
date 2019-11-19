package mm_districting;

import util.Geography;
import util.Voting;

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
public class District{

    private Set<Precinct> precincts;

    private DemographicContext demographics;
    private Voting             votingData;

    private Geography geography;
    private long      geoId;
    private int       districtNumber;

    public District(){
        precincts = new HashSet<>();
    }

    /**
     * @return The given Cluster converted to a District object.
     */
    public static District fromCluster(Cluster cluster){
        District d = new District();
        d.precincts = cluster.getPrecincts();
        return d;
    }

    @Id
    @Column(name = "GEO_ID")
    public long getGeoId(){
        return this.geoId;
    }

    public boolean addPrecinct(Precinct p){
        return precincts.add(p);
    }

    public boolean removePrecinct(Precinct p){
        return precincts.remove(p);
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "PRECINCT_ID")
    public Set<Precinct> getPrecincts(){
        return precincts;
    }

    public void setPrecincts(Set<Precinct> precincts){
        this.precincts = precincts;
    }

    public DemographicContext getDemographics(){
        return demographics;
    }

    public void setDemographics(DemographicContext demographics){
        this.demographics = demographics;
    }

    public Voting getVotingData(){
        return votingData;
    }

    public void setVotingData(Voting votingData){
        this.votingData = votingData;
    }

    public Geography getGeography(){
        return geography;
    }

    public void setGeography(Geography geography){
        this.geography = geography;
    }


}
