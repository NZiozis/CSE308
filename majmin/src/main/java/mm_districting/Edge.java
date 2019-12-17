package mm_districting;

import algorithm_steps.DummyEdge;

/**
 * Represents an edge between two clusters, used to store how joinable these two clusters are.
 *
 * @author Patrick Wamsley
 * @author Brett Weinger
 * @author Felix Rieg-Baumhauer
 * @see Cluster
 * @see Joinability
 */
public class Edge {

    private Cluster clusterOne;
    private Cluster clusterTwo;

    /**
     * The overall joinability value of this edge based on all available information.
     */
    private double joinability;

    /**
     * The joinability value of this edge based only on it's majority/minority demographic data
     */
    private double majorityMinorityJoinability;

    public Edge(Cluster clusterOne, Cluster clusterTwo) {
        this.clusterOne = clusterOne;
        this.clusterTwo = clusterTwo;
    }

    /**
     * @return The partner cluster in this edge if the edge contains this cluster, null otherwise.
     */
    public Cluster getAdjacentCluster(Cluster cluster) {
        if (cluster.equals(clusterOne)) {
            return clusterTwo;
        }
        else if (cluster.equals(clusterTwo)) {
            return clusterOne;
        }
        else {
            return null;
        }
    }

    public double getJoinability() {
        return joinability;
    }

    public void setJoinability(double joinability) {
        this.joinability = joinability;
    }

    public double getMajMinJoinability() {
        return majorityMinorityJoinability;
    }

    public Cluster getClusterOne() {
        return clusterOne;
    }

    public Cluster getClusterTwo() {
        return clusterTwo;
    }

    public void setMajorityMinorityJoinability(double majorityMinorityJoinability) {
        this.majorityMinorityJoinability = majorityMinorityJoinability;
    }

    /**
     * @return True iff this edge contains any cluster in the provided cluster array
     */
    public boolean hasClusters(Cluster... clusters) {
        boolean hasOne = false;
        for (Cluster c : clusters) {
            if (clusterOne.equals(c) || clusterTwo.equals(c)) {
                hasOne = true;
            }
        }
        return hasOne;
    }

    public void updateCluster(Cluster combinedCluster, Cluster c1, Cluster c2) {
        if (clusterOne.equals(c1) || clusterOne.equals(c2)) {
            clusterOne = combinedCluster;
        } else if (clusterTwo.equals(c1) || clusterTwo.equals((c2))) {
            clusterTwo = combinedCluster;
        }
    }

    public boolean isSameEdge(Edge other) {
        return (clusterOne.equals(other.clusterOne) || clusterOne.equals(other.clusterTwo)) && (clusterTwo.equals(other.clusterOne) || clusterTwo.equals(other.clusterTwo));
    }

    @Override
    public int hashCode() {

        if (this instanceof DummyEdge) {
            return -1;
        }

        int hash = 17;
        hash = hash * 23 + clusterOne.hashCode();
        hash = hash * 23 + clusterTwo.hashCode();
        return hash;
    }

}
