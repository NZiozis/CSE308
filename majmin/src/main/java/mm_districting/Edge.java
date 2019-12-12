package mm_districting;

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

    public boolean hasCluster(Cluster cluster) {
        return clusterOne.equals(cluster) || clusterTwo.equals(cluster);
    }

    public void updateCluster(Cluster combinedCluster, Cluster c1, Cluster c2) {
        if (clusterOne.equals(c1) || clusterOne.equals(c2)) {
            clusterOne = combinedCluster;
        } else if (clusterTwo.equals(c1) || clusterTwo.equals((c2))) {
            clusterTwo = combinedCluster;
        }
    }
}
