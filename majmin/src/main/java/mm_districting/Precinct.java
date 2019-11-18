package mm_districting;

import util.Party;
import util.Race;
import util.Voting;

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
public class Precinct{

    private String county;
    private int    legal_id;
    private int    table_id;

    private DemographicContext demographics;
    private Voting[]           votingList;
    private Race               demographicBloc;
    private Party              partyBloc;

    private Set<Precinct> neighbors;


    public Precinct(String county, int legal_id){
        this.county = county;
        this.legal_id = legal_id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "PRECINCT_ID")
    public int getTable_id(){
        return table_id;
    }

    //TODO
    public double getDemographicPercent(Race race){
        return 0;
    }

    public String getCounty(){
        return county;
    }

    public void setCounty(String county){
        this.county = county;
    }

    @Column(name = "LEGAL_ID")
    public int getLegal_id(){
        return legal_id;
    }

    public void setLegal_id(int legal_id){
        this.legal_id = legal_id;
    }

    public DemographicContext getDemographics(){
        return demographics;
    }

    public void setDemographics(DemographicContext demographics){
        this.demographics = demographics;
    }

    public Voting[] getVotingList(){
        return votingList;
    }

    public void setVotingList(Voting[] votingList){
        this.votingList = votingList;
    }

    public Race getDemographicBloc(){
        return demographicBloc;
    }

    public void setDemographicBloc(Race demographicBloc){
        this.demographicBloc = demographicBloc;
    }

    public Party getPartyBloc(){
        return partyBloc;
    }

    public void setPartyBloc(Party partyBloc){
        this.partyBloc = partyBloc;
    }

	//TODO Determine how the annotations should work here
    public Set<Precinct> getNeighbors(){
        return neighbors;
    }

    public void setNeighbors(Set<Precinct> neighbors){
        this.neighbors = neighbors;
    }


}
