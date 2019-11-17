package mm_districting;

import java.util.Map;
import java.util.Set;

import util.Operation;
import util.Voting;

/**
 * Represents a State, including encompassed precincts, districts, demographics, voting data, and algorithm-oriented objects such as clusters and edges.
 * 
 * @author Patrick Wamsley
 * @author Brett Weinger
 * @author Felix Rieg-Baumhauer
 * @author Niko Ziozis
 */
public class State {

	/**
	 * Used to identify which state the user has selected to be analyized. 
	 * @see AlgorithmProperties.java
	 * @see AlgorithmManager.java
	 * @see Operation.java
	 */
	public static enum StateID {WEST_VIRGINIA, UTAH, FLORIDA}
	
	//---state data---//
	private String name; 
	private String legalGuidelines;
	private DemographicContext demographicData; 
	private Voting votingData;
	
	//---Encompassed geographical objects---//
	private Set<Precinct> precincts;
	private Set<District> initialDistricts;
	
	//---Algorithm oriented objects---//
	private Set<District> generatedDistricts;
	private Set<Cluster> clusters;
	private Map<Cluster, Edge> bestPairings;
	private Set<Cluster> doNotPairClusters;
	
	public State(String name) {
		this.name = name;
	}
	
	/**
	 * @return The previously set highest joinability found for this cluster.
	 */
	public float getMaxJoinability(Cluster cluster) {
		return bestPairings.get(cluster).getJoinability();
	}
	
	/**
	 * @return The Cluster's most joinable edge, or null if it currently has no pairing.
	 */
	public Edge getMostJoinableEdge(Cluster cluster) {
		return bestPairings.get(cluster);
	}
	
	/**
	 * Sets the given cluster's most joinabile edge.
	 */
	public void updateMostJoinable(Cluster cluster, Edge newEdge) {
		bestPairings.put(cluster, newEdge);
	}
	
	//TODO
	/**
	 * Updates the Set of Clusters in this state by replacing the two clusters given by the edge with their combined cluster.
	 */
	public void combineClusters(Edge edge) {
		
	}
	
	public boolean isClusterAlreadyPaired(Cluster cluster) {
		return doNotPairClusters.contains(cluster);
	}

	public String getName() {
		return name;
	}

	public String getLegalGuidelines() {
		return legalGuidelines;
	}

	public DemographicContext getDemographicData() {
		return demographicData;
	}

	public Voting getVotingData() {
		return votingData;
	}

	public Set<Precinct> getPrecincts() {
		return precincts;
	}

	public Set<District> getInitialDistricts() {
		return initialDistricts;
	}

	public Set<District> getGeneratedDistricts() {
		return generatedDistricts;
	}

	public Set<Cluster> getClusters() {
		return clusters;
	}

	public Set<Cluster> getDoNotPairClusters() {
		return doNotPairClusters;
	}

	public void setLegalGuidelines(String legalGuidelines) {
		this.legalGuidelines = legalGuidelines;
	}

	public void setDemographicData(DemographicContext demographicData) {
		this.demographicData = demographicData;
	}

	public void setVotingData(Voting votingData) {
		this.votingData = votingData;
	}

	public void setPrecincts(Set<Precinct> precincts) {
		this.precincts = precincts;
	}

	public void setInitialDistricts(Set<District> initialDistricts) {
		this.initialDistricts = initialDistricts;
	}

	public void setGeneratedDistricts(Set<District> generatedDistricts) {
		this.generatedDistricts = generatedDistricts;
	}

	public void setClusters(Set<Cluster> clusters) {
		this.clusters = clusters;
	}

	public void setDoNotPairClusters(Set<Cluster> doNotPairClusters) {
		this.doNotPairClusters = doNotPairClusters;
	}
	
	
}
