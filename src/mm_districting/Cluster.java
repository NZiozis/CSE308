package mm_districting;

import java.util.HashSet;
import java.util.Set;

/**
 * A cluster in the graph partioning algorithm, representative of a State district. 
 * 
 * @see Edge.java
 * @see Joinability.java
 * @see District.java
 * 
 * @author Patrick Wamsley
 * @author Brett Weinger
 * @author Felix Rieg-Baumhauer
 */
public class Cluster {
	
	//---used to identify clusters---/
	private static int clusterCount;
	private int id;
	
	private Set<Precinct> precincts;
	private Set<Edge> edges;
	
	public Cluster() {
		id = clusterCount++;
		
		precincts = new HashSet<>();
		edges = new HashSet<>();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Cluster)) {
			return false;
		} else {
			return this.id == ((Cluster)obj).id;
		}
	}
	
	public void addPrecinct(Precinct p) {
		precincts.add(p);
	}
	
	public void addEdge(Edge e) {
		edges.add(e);
	}

	public Set<Precinct> getPrecincts() {
		return precincts;
	}

	public Set<Edge> getEdges() {
		return edges;
	}
	
}
