package mm_districting;

import java.util.HashSet;
import java.util.Set;

import util.Geography;
import util.Voting;

/**
 * Represents a district object, containing geographic data as well as voting and demographic data.
 * @author Patrick Wamsley
 * @author Brett Weinger
 * @author Felix Rieg-Baumhauer
 */
public class District {
	
	private Set<Precinct> precincts;
	
	private DemographicContext demographics;
	private Voting votingData;
	
	private Geography geography;

	public District() {
		precincts = new HashSet<>();
	}
	
	/**
	 *
	 * @return The given Cluster converted to a District object.
	 */
	public static District fromCluster(Cluster cluster) {
		District d = new District();
		d.precincts = cluster.getPrecincts();
		return d;
	}
	
	public boolean addPrecinct(Precinct p) {
		return precincts.add(p);
	}
	
	public boolean removePrecinct(Precinct p) {
		return precincts.remove(p);
	}

	public Set<Precinct> getPrecincts() {
		return precincts;
	}

	public DemographicContext getDemographics() {
		return demographics;
	}

	public Voting getVotingData() {
		return votingData;
	}

	public Geography getGeography() {
		return geography;
	}

	public void setPrecincts(Set<Precinct> precincts) {
		this.precincts = precincts;
	}

	public void setVotingData(Voting votingData) {
		this.votingData = votingData;
	}

	public void setDemographics(DemographicContext demographics) {
		this.demographics = demographics;
	}

	public void setGeography(Geography geography) {
		this.geography = geography;
	}
	
	
}
