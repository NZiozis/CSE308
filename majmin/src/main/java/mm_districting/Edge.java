package mm_districting;

/**
 * Represents an edge between two clusters, used to store how joinable these two clusters are.
 * 
 * @see Cluster
 * @see Joinability
 * 
 * @author Patrick Wamsley
 * @author Brett Weinger
 * @author Felix Rieg-Baumhauer
 */
public class Edge {
	
	private Cluster clusterOne;
	private Cluster clusterTwo;
	
	/**
	 * The overall joinability value of this edge based on all available information.
	 */
	private float joinability;
	
	/**
	 * The joinability value of this edge based only on it's majority/minority demographic data
	 */
	private float majorityMinorityJoinability;
	
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
		} else if (cluster.equals(clusterTwo)) {
			return clusterOne;
		} else {
			return null;
		}
	}
	
	public float getJoinability() {
		return joinability;
	}
	
	public float getMajMinJoinability() {
		return majorityMinorityJoinability;
	}

	public Cluster getClusterOne() {
		return clusterOne;
	}

	public Cluster getClusterTwo() {
		return clusterTwo;
	}

	public void setJoinability(float joinability) {
		this.joinability = joinability;
	}

	public void setMajorityMinorityJoinability(float majorityMinorityJoinability) {
		this.majorityMinorityJoinability = majorityMinorityJoinability;
	}
	
	

}
