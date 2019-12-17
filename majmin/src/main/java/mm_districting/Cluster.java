package mm_districting;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A cluster in the graph partioning algorithm, representative of a State district. 
 * 
 * @see Edge
 * @see Joinability
 * @see District
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

	private Set<Voting> votingData;
	private DemographicContext demographicContext;

	private Edge removedWithEdge;

	private boolean isCombinedCluster = false;

	public Cluster() {
		id = clusterCount++;
		
		precincts = new HashSet<>();
		edges = new HashSet<>();
	}

	public Cluster(boolean isCombindedCluster) {
		this();
		this.isCombinedCluster = isCombindedCluster;
	}

	public void setRemovedWithEdge(Edge e) {
		this.removedWithEdge = e;
	}

	public boolean isCombinedCluster() {
		return isCombinedCluster;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Cluster)) {
			return false;
		} else {
			return this.id == ((Cluster)obj).id;
		}
	}

	public Set<Voting> getVotingData() {
		return votingData;
	}

	@Override
	public int hashCode() {
		return id;
	}
	
	public void addPrecinct(Precinct p) {
		if (precincts.size() == 0) {
			demographicContext = new DemographicContext();
			demographicContext.setAfricanAmerican(p.getDemographics().getAfricanAmerican());
			demographicContext.setAmericanIndian(p.getDemographics().getAmericanIndian());
			demographicContext.setAsian(p.getDemographics().getAsian());
			demographicContext.setPacificIslander(p.getDemographics().getPacificIslander());
			demographicContext.setWhite(p.getDemographics().getWhite());
			demographicContext.setOther(p.getDemographics().getOther());
			demographicContext.setTotal(p.getDemographics().getTotal());
		} else {
			demographicContext.setAfricanAmerican(demographicContext.getAfricanAmerican() + p.getDemographics().getAfricanAmerican());
			demographicContext.setAmericanIndian(demographicContext.getAmericanIndian() + p.getDemographics().getAmericanIndian());
			demographicContext.setAsian(demographicContext.getAsian() + p.getDemographics().getAsian());
			demographicContext.setPacificIslander(demographicContext.getPacificIslander() + p.getDemographics().getPacificIslander());
			demographicContext.setWhite(demographicContext.getWhite() + p.getDemographics().getWhite());
			demographicContext.setOther(demographicContext.getOther() + p.getDemographics().getOther());
			demographicContext.setTotal(demographicContext.getTotal() + p.getDemographics().getTotal());
		}

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

	public void setEdges(Set<Edge> edges) {
		this.edges = edges;
	}
	public DemographicContext getDemographicContext() {
		return demographicContext;
	}

	public void setDemographicContext(DemographicContext context) {
		this.demographicContext = context;
	}

}
