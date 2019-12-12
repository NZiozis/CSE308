package mm_districting;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import util.Operation;

import javax.persistence.*;

import java.util.HashMap;
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
@JsonIgnoreProperties(value = { "initialDistricts", "generatedDistricts", "clusters", "bestPairings", "geography" })
public class State {

    //---state data---//
    private String             name;
    private long               stateId;
    private String             legalGuidelines;
    private Set<Voting>        votingSet;
    private DemographicContext demographicContext;
    //---Encompassed geographical objects---//
    private Set<District>      initialDistricts;
    //---Algorithm oriented objects---//
    private Set<District>     generatedDistricts;
    private Set<Cluster>      clusters;
    @Transient
    private Set<Edge>         edges;
    private Map<Cluster,Edge> bestPairings;
    private Set<Cluster>      doNotPairClusters;
    private String            geography;


    @Transient
    private Map<Precinct, Cluster> initialClustersMap;

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
        this.initialClustersMap = new HashMap<>();
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
    public double getMaxJoinability(Cluster cluster) {
        //TODO: probably combine MajMin and normal joinability, simply making majmin weight much higher than anything else
        Edge bestEdge = bestPairings.get(cluster);
        return bestEdge == null ? -1 : Math.max(bestEdge.getMajMinJoinability(), bestEdge.getJoinability());
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
        clusters.remove(edge.getClusterOne());
        clusters.remove(edge.getClusterTwo());
        doNotPairClusters.add(edge.getClusterOne());
        doNotPairClusters.add(edge.getClusterTwo());

        Cluster combinedCluster = new Cluster();
        combinedCluster.getPrecincts().addAll(edge.getClusterOne().getPrecincts());
        combinedCluster.getPrecincts().addAll(edge.getClusterTwo().getPrecincts());
        combinedCluster.setEdges(edge.getClusterOne().getEdges());
        combinedCluster.getEdges().addAll(edge.getClusterTwo().getEdges());
        combinedCluster.getEdges().remove(edge);

        for (Edge e : combinedCluster.getEdges()) {
            e.updateCluster(combinedCluster, edge.getClusterOne(), edge.getClusterTwo());
        }

    }

    @Column(name = "GEOGRAPHY", length = 16777215, columnDefinition = "mediumtext", nullable = false)
    public String getGeography() {
        return geography;
    }

    public void setGeography(String geography) {
        this.geography = geography;
    }

    public boolean isClusterAlreadyPaired(Cluster cluster) {
        return doNotPairClusters.contains(cluster);
    }

    @Transient
    public Set<Precinct> getPrecincts() {
        HashSet<Precinct> output = new HashSet<>();

//        initialDistricts.iterator(District district -> output.addAll(district.getPrecincts() ));
//        Iterator<District> iterator = initialDistricts.iterator();
//        while (iterator.hasNext()) {
//            District d = iterator.next();
//            output.addAll();
//        }
        for (District district : initialDistricts) {
            output.addAll(district.getPrecincts());
        }
        return output;
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "STATE_TO_VOTING")
    @JoinColumn(name = "VOTING_DATA_ID")
    public Set<Voting> getVotingSet() {
        return votingSet;
    }

    public void setVotingSet(Set<Voting> votingSet) {
        this.votingSet = votingSet;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "STATE_TO_DEMOGRAPHIC")
    @JoinColumn(name = "CONTEXT_ID")
    public DemographicContext getDemographicContext() {
        return demographicContext;
    }

    public void setDemographicContext(DemographicContext demographicContext) {
        this.demographicContext = demographicContext;
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

    @Transient
    public Set<Edge> getEdges() {
        return edges;
    }

    @Transient
    public Map<Precinct, Cluster> getInitialClustersMap() {
        return initialClustersMap;
    }

    public void addClusterPrecinctMapping(Precinct precinct, Cluster cluster) {
        initialClustersMap.put(precinct, cluster);
    }

    @Transient
    public HashMap<Cluster, Edge> getBestPairings() {
        return (HashMap<Cluster, Edge>)bestPairings;
    }

    /**
     * Used to identify which state the user has selected to be analyzed.
     * This matches up with the DB
     *
     * @see AlgorithmProperties
     * @see AlgorithmManager
     * @see Operation
     */

    public enum StateID {WEST_VIRGINIA, FLORIDA, UTAH}

}
