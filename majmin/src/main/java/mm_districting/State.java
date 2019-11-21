package mm_districting;

import util.Operation;

import javax.persistence.*;
import java.util.HashSet;
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
    private String            name;
    private long              stateId;
    private String            legalGuidelines;
    private Voting            votingData;
    //---Encompassed geographical objects---//
    private Set<District>     initialDistricts;
    //---Algorithm oriented objects---//
    private Set<District>     generatedDistricts;
    private Set<Cluster>      clusters;
    private Map<Cluster,Edge> bestPairings;
    private Set<Cluster>      doNotPairClusters;
    private String            geography;

    public State() {}

    public State(String name) {
        this.name = name;
    }

    public State(String name, int stateId, String legalGuidelines, String geography) {
        this.name = name;
        this.stateId = stateId;
        this.legalGuidelines = legalGuidelines;
        this.geography = geography;
        this.initialDistricts = new HashSet<>();
    }

    @Id
    @Column(name = "STATE_ID")
    public long getStateId() {
        return this.stateId;
    }

    public void setStateId(long stateId) {
        this.stateId = stateId;
    }

    /**
     * @return The previously set highest joinability found for this cluster.
     */
    @Transient
    public float getMaxJoinability(Cluster cluster) {
        return bestPairings.get(cluster).getJoinability();
    }

    /**
     * @return The Cluster's most joinable edge, or null if it currently has no pairing.
     */
    @Transient
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

    @Column(name = "GEOGRAPHY", length = 16777215, columnDefinition = "mediumtext", nullable = false)
    public String getGeography() {
        return geography;
    }

    public void setGeography(String geography) {
        this.geography = geography;
    }
//TODO

    public boolean isClusterAlreadyPaired(Cluster cluster) {
        return doNotPairClusters.contains(cluster);
    }

    @Column(name = "STATE_NAME", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLegalGuidelines() {
        return legalGuidelines;
    }

    public void setLegalGuidelines(String legalGuidelines) {
        this.legalGuidelines = legalGuidelines;
    }

    @Transient
    public Voting getVotingData() {
        return votingData;
    }

    public void setVotingData(Voting votingData) {
        this.votingData = votingData;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "STATE_TO_DISTRICT")
    @JoinColumn(name = "DISTRICT_ID")
    public Set<District> getInitialDistricts() {
        return initialDistricts;
    }

    public void setInitialDistricts(Set<District> initialDistricts) {
        this.initialDistricts = initialDistricts;
    }

    public boolean addDistrict(District d) {
        return initialDistricts.add(d);
    }

    //TODO Placeholder until district name is decided on
    @Transient
    public Set<District> getGeneratedDistricts() {
        return generatedDistricts;
    }

    public void setGeneratedDistricts(Set<District> generatedDistricts) {
        this.generatedDistricts = generatedDistricts;
    }

    @Transient
    public Set<Cluster> getClusters() {
        return clusters;
    }

    public void setClusters(Set<Cluster> clusters) {
        this.clusters = clusters;
    }

    @Transient
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
