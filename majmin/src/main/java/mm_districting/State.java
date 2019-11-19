package mm_districting;

import util.Operation;
import util.Voting;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;

/**
 * Represents a State, including encompassed precincts, districts, demographics, voting data, and algorithm-oriented objects such as clusters and edges.
 *
 * @author Patrick Wamsley
 * @author Brett Weinger
 * @author Felix Rieg-Baumhauer
 * @author Niko Ziozis
 */
@Entity
@Table(name = "STATE")
public class State {

    //---state data---//
    private String             name;
    private int                stateId;
    private String             legalGuidelines;
    private DemographicContext demographicData;
    private Voting             votingData;
    //---Encompassed geographical objects---//
    private Set<Precinct>      precincts;
    private Set<District>      initialDistricts;
    //---Algorithm oriented objects---//
    private Set<District>      generatedDistricts;
    private Set<Cluster>       clusters;
    private Map<Cluster,Edge>  bestPairings;
    private Set<Cluster>       doNotPairClusters;

    public State(String name) {
        this.name = name;
    }

    @Id
    @Column(name = "STATE_ID")
    public int getStateId(){
        return this.stateId;
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

    /**
     * Updates the Set of Clusters in this state by replacing the two clusters given by the edge with their combined cluster.
     */
    public void combineClusters(Edge edge) {

    }

    //TODO

    public boolean isClusterAlreadyPaired(Cluster cluster) {
        return doNotPairClusters.contains(cluster);
    }

    //TODO add pk for state

    @Column(name = "STATE_NAME", nullable = false)
    public String getName() {
        return name;
    }

    //TODO column add as string
    public String getLegalGuidelines() {
        return legalGuidelines;
    }

    public void setLegalGuidelines(String legalGuidelines) {
        this.legalGuidelines = legalGuidelines;
    }

    public DemographicContext getDemographicData() {
        return demographicData;
    }

    public void setDemographicData(DemographicContext demographicData) {
        this.demographicData = demographicData;
    }

    public Voting getVotingData() {
        return votingData;
    }

    public void setVotingData(Voting votingData) {
        this.votingData = votingData;
    }

    public Set<Precinct> getPrecincts() {
        return precincts;
    }

    public void setPrecincts(Set<Precinct> precincts) {
        this.precincts = precincts;
    }

    public Set<District> getInitialDistricts() {
        return initialDistricts;
    }

    public void setInitialDistricts(Set<District> initialDistricts) {
        this.initialDistricts = initialDistricts;
    }

    //TODO Placeholder until district name is decided on
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "DISTRICT_ID")
    public Set<District> getGeneratedDistricts() {
        return generatedDistricts;
    }

    public void setGeneratedDistricts(Set<District> generatedDistricts) {
        this.generatedDistricts = generatedDistricts;
    }

    public Set<Cluster> getClusters() {
        return clusters;
    }

    public void setClusters(Set<Cluster> clusters) {
        this.clusters = clusters;
    }

    public Set<Cluster> getDoNotPairClusters() {
        return doNotPairClusters;
    }

    public void setDoNotPairClusters(Set<Cluster> doNotPairClusters) {
        this.doNotPairClusters = doNotPairClusters;
    }

    /**
     * Used to identify which state the user has selected to be analyzed.
     *
     * @see AlgorithmProperties
     * @see AlgorithmManager
     * @see Operation
     */
    public enum StateID {WEST_VIRGINIA, UTAH, FLORIDA}


}
