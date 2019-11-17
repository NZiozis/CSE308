package mm_districting;

import java.util.Set;

import util.Party;
import util.Race;
import util.Voting;

/**
 * Represents a Precinct, which are combined to create Voting districts. 
 * 
 * Contains geographic data as well as voting and demographic bloc data.
 * 
 * @author Patrick Wamsley
 * @author Brett Weinger
 * @author Felix Rieg-Baumhauer
 */
public class Precinct {
	
	private String county;
	private int id;
	
	private DemographicContext demographics;
	private Voting[] votingList;
	private Race demographicBloc;
	private Party partyBloc;
	
	private Set<Precinct> neighbors;
	
	
	public Precinct(String county, int id) {
		this.county = county;
		this.id = id;
	}
	
	//TODO
	public double getDemographicPercent(Race race) {
		return 0;
	}

	public String getCounty() {
		return county;
	}

	public int getId() {
		return id;
	}

	public DemographicContext getDemographics() {
		return demographics;
	}

	public Voting[] getVotingList() {
		return votingList;
	}

	public Race getDemographicBloc() {
		return demographicBloc;
	}

	public Party getPartyBloc() {
		return partyBloc;
	}

	public Set<Precinct> getNeighbors() {
		return neighbors;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDemographics(DemographicContext demographics) {
		this.demographics = demographics;
	}

	public void setVotingList(Voting[] votingList) {
		this.votingList = votingList;
	}

	public void setDemographicBloc(Race demographicBloc) {
		this.demographicBloc = demographicBloc;
	}

	public void setPartyBloc(Party partyBloc) {
		this.partyBloc = partyBloc;
	}

	public void setNeighbors(Set<Precinct> neighbors) {
		this.neighbors = neighbors;
	}
	

}
