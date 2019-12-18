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
@JsonIgnoreProperties(
        value = { "initialDistricts", "generatedDistricts", "clusters", "bestPairings", "geography", "edges",
                  "bestPairings", "doNotPairClusters", "precincts" })
public class State {

    @Transient public  Set<Cluster>        discardedClusters = new HashSet<>();
    //---state data---//
    private            String              name;
    private            long                stateId;
    private            String              legalGuidelines;
    private            Set<Voting>         votingSet;
    private            DemographicContext  demographicContext;
    //---Encompassed geographical objects---//
    private            Set<District>       initialDistricts;
    //---Algorithm oriented objects---//
    private            Set<District>       generatedDistricts;
    private            Set<Cluster>        clusters;
    @Transient private  HashMap<Cluster, HashMap<Cluster, Edge>> edgeHash = new HashMap<>();

    //    @Transient private Set<Edge>          edges;
    private            String              geography;
    @Transient private Edge                nextEdgeToCombine;
    private            Map<String,Integer> incumbentData;
    //maps precinct GeoID to Cluster
    @Transient private Map<String,Cluster> initialClustersMap;

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
    }

    @Transient
    public Map<String,Integer> getIncumbentData() {
        if (this.incumbentData == null) {
            this.incumbentData = new HashMap<>();
        }
        this.incumbentData.put("republican", 0);
        this.incumbentData.put("democrat", 0);
        for (District d : initialDistricts) {
            String incumbent = d.getIncumbent();
            if (incumbent.contains("(R)")) {
                this.incumbentData.put("republican", ( this.incumbentData.get("republican") + 1 ));
            }
            else {
                this.incumbentData.put("democrat", ( this.incumbentData.get("democrat") + 1 ));
            }
        }
        return incumbentData;
    }

    public void setIncumbentData(Map<String,Integer> incumbentData) {
        this.incumbentData = incumbentData;
    }

    /**
     * Updates the Set of Clusters in this state by replacing the two clusters given by the edge with their combined cluster.
     */
    public void combineClusters(Edge edge) {

        if (clusters.size() == 1853) {
            System.out.print("");
        }

        boolean c1In = !clusters.contains(edge.getClusterOne());
        boolean c2In = !clusters.contains(edge.getClusterTwo());

        if (!clusters.contains(edge.getClusterOne()) || !clusters.contains(edge.getClusterTwo())) {
            return;
        }

        Cluster combinedCluster = new Cluster(true);
        for (Precinct p : edge.getClusterOne().getPrecincts()) {
            combinedCluster.addPrecinct(p);
        }

        for (Precinct p : edge.getClusterTwo().getPrecincts()) {
            combinedCluster.addPrecinct(p);
        }

        combinedCluster.setDemographicContext(DemographicContext.combine(edge.getClusterOne().getDemographicContext(),
                                                                         edge.getClusterTwo().getDemographicContext()));
        combinedCluster.setVotingData(
                Voting.combineVotingSets(edge.getClusterOne().getVotingData(), edge.getClusterTwo().getVotingData()));

        //set neighbors
        Set<Cluster> clusterOneNeighbors = edge.getClusterOne().getNeighbors();
        Set<Cluster> clusterTwoNeighbors = edge.getClusterTwo().getNeighbors();

        for (Cluster c : clusterOneNeighbors) {
            combinedCluster.addNeighbor(c);
        }

        for (Cluster c : clusterTwoNeighbors) {
            combinedCluster.addNeighbor(c);
        }

        combinedCluster.removeNeighbor(edge.getClusterOne());
        combinedCluster.removeNeighbor(edge.getClusterTwo());

        boolean x = clusters.add(combinedCluster);
        boolean y = clusters.remove(edge.getClusterOne());
        boolean z = clusters.remove(edge.getClusterTwo());

        for (Cluster c : combinedCluster.getNeighbors()) {
            boolean a = c.removeNeighbor(edge.getClusterOne());
            boolean b = c.removeNeighbor(edge.getClusterTwo());
            if (a || b) {
                c.addNeighbor(combinedCluster);
            }
        }
        HashMap<Cluster, Edge> tempHash = new HashMap<Cluster, Edge>();
        edgeHash.put(combinedCluster, tempHash);

        discardedClusters.add(edge.getClusterOne());
        discardedClusters.add(edge.getClusterTwo());

//        for (Edge e : edges) {
//            e.updateCluster(combinedCluster, edge.getClusterOne(), edge.getClusterTwo());
//        }
    }

    @Column(name = "GEOGRAPHY", length = 16777215, columnDefinition = "mediumtext", nullable = false)
    public String getGeography() {
        return geography;
    }

    public void setGeography(String geography) {
        this.geography = geography;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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

//    @Transient
//    public Set<Edge> getEdges() {
//        return edges;
//    }
//
//    public void setEdges(Set<Edge> edges) {
//        this.edges = edges;
//    }

    @Transient
    public Map<String,Cluster> getInitialClustersMap() {
        return initialClustersMap;
    }

    public void addClusterPrecinctMapping(Precinct precinct, Cluster cluster) {
        initialClustersMap.put(precinct.getGeoId(), cluster);
    }

    public void init() {
        initialClustersMap = new HashMap<>();
    }

    @Transient
    public Edge getNextEdgeToCombine() {
        return nextEdgeToCombine;
    }

    @Transient
    public void setNextEdgeToCombine(Edge edge) {
        nextEdgeToCombine = edge;
    }

    public double getMaxJoinability(boolean doingMM) {
        if (nextEdgeToCombine == null) {
            return -1;
        }
        return doingMM ? nextEdgeToCombine.getMajMinJoinability() : nextEdgeToCombine.getJoinability();
    }


    @Transient
    public void setEdgeHash(HashMap<Cluster, HashMap<Cluster, Edge>> hashMap){
        edgeHash = hashMap;
    }

    @Transient
    public HashMap<Cluster, HashMap<Cluster, Edge>> getEdgeHash(){
        return edgeHash;
    }

    @Transient
    public void setEdgeListing(Cluster c1, Cluster c2, Edge e){
        edgeHash.get(c1).put(c2, e);
        edgeHash.get(c2).put(c1, e);
    }

    @Transient
    public Edge getEdgeListing(Cluster c1, Cluster c2){
        return edgeHash.get(c1).get(c2);
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
