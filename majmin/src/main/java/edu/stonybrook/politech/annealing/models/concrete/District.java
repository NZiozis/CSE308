package edu.stonybrook.politech.annealing.models.concrete;

import edu.stonybrook.politech.annealing.measures.DistrictInterface;
import org.locationtech.jts.algorithm.MinimumBoundingCircle;
import org.locationtech.jts.geom.*;

import java.util.*;

public class District
        implements DistrictInterface<Precinct>
{
    private String ID;
    private State state;

    private static final int MAXX = 0;
    private static final int MAXY = 1;
    private static final int MINX = 2;
    private static final int MINY = 3;

    private int population;

    private int gop_vote;
    private int dem_vote;

    private int internalEdges = 0;
    private int externalEdges = 0;

    private HashMap<String, Precinct> precincts;
    private Set<Precinct> borderPrecincts;

    private MultiPolygon multiPolygon;

    private Geometry boundingCircle;
    private Geometry convexHull;

    private boolean boundingCircleUpdated=false;
    private boolean multiPolygonUpdated=false;
    private boolean convexHullUpdated=false;

    /*public ConcreteDistrict(String ID) {
            this(ID, null);
        }*/

    public District(String ID, State state ) {
        this.ID = ID;
        population = 0;
        gop_vote = 0;
        dem_vote = 0;
        precincts = new HashMap<String, Precinct>();
        borderPrecincts = new HashSet<Precinct>();
        this.state = state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public String getID() {
        return ID;
    }

    public int getPopulation() {
        return population;
    }

    public int getGOPVote() {
        return gop_vote;
    }

    public int getDEMVote() {
        return dem_vote;
    }

    public int getInternalEdges() {
        return internalEdges;
    }

    public int getExternalEdges() {
        return externalEdges;
    }

    private Set<Precinct> getInternalNeighbors(Precinct p) {
        Set<Precinct> neighborsInternal = new HashSet<>();
        for (String nid : p.getNeighborIDs()) {
            if (precincts.containsKey(nid)) {
                Precinct neighbor = precincts.get(nid);
                neighborsInternal.add(neighbor);
            }
        }
        return neighborsInternal;
    }

    public Set<Precinct> getPrecincts() {
        Set<Precinct> to_return = new HashSet<Precinct>();
        for (Precinct p : precincts.values()) {
            to_return.add(p);
        }
        return to_return;
    }

    public String[] getPrecinctIDs() {
        return (String[])precincts.keySet().toArray();
    }

    public Set<Precinct> getBorderPrecincts() {
        return new HashSet<>(borderPrecincts);
    }

    public boolean isBorderPrecinct(Precinct precinct) {
        for (String neighborID : precinct.getNeighborIDs()) {
            //if the neighbor's neighbor is not in the district, then it is outer
            if (!precincts.containsKey(neighborID))  {
                return true;
            }
        }
        return false;
    }

    public Precinct getPrecinct(String PrecID) {
        return precincts.get(PrecID);
    }

    public void addPrecinct(Precinct p) {
        precincts.put(p.getID(), p);
        population += p.getPopulation();
        gop_vote += p.getGOPVote();
        dem_vote += p.getDEMVote();
        borderPrecincts.add(p);
        Set<Precinct> newInternalNeighbors = getInternalNeighbors(p);
        int newInternalEdges = newInternalNeighbors.size();
        internalEdges += newInternalEdges;
        externalEdges -= newInternalEdges;
        externalEdges += (p.getNeighborIDs().size() - newInternalEdges);
        newInternalNeighbors.removeIf(
                this::isBorderPrecinct
        );
        borderPrecincts.removeAll(newInternalNeighbors);

        this.multiPolygonUpdated = false;
        this.convexHullUpdated = false;
        this.boundingCircleUpdated = false;
    }

    public void removePrecinct(Precinct p) {
        precincts.remove(p.getID());
        population -= p.getPopulation();
        gop_vote -= p.getGOPVote();
        dem_vote -= p.getDEMVote();
        Set<Precinct> lostInternalNeighbors = getInternalNeighbors(p);
        int lostInternalEdges = lostInternalNeighbors.size();
        internalEdges -= lostInternalEdges;
        externalEdges += lostInternalEdges;
        externalEdges -= (p.getNeighborIDs().size() - lostInternalEdges);
        borderPrecincts.remove(p);
        borderPrecincts.addAll(lostInternalNeighbors);

        this.multiPolygonUpdated = false;
        this.convexHullUpdated = false;
        this.boundingCircleUpdated = false;
    }


    public MultiPolygon computeMulti() {
        Polygon[] polygons = new Polygon[getPrecincts().size()];

        Iterator<Precinct> piter = getPrecincts().iterator();
        for(int ii = 0; ii < polygons.length; ii++) {
            Geometry poly = piter.next().getGeometry();
            if (poly instanceof Polygon)
                polygons[ii] = (Polygon) poly;
            else
                polygons[ii] = (Polygon) poly.convexHull();
        }
        MultiPolygon mp = new MultiPolygon(polygons,new GeometryFactory());
        this.multiPolygon = mp;
        this.multiPolygonUpdated = true;
        return mp;
    }

    public MultiPolygon getMulti() {
        if (this.multiPolygonUpdated && this.multiPolygon != null)
            return this.multiPolygon;
        return computeMulti();
    }

    public Geometry getConvexHull() {
        if (convexHullUpdated && convexHull !=null)
            return convexHull;
        convexHull = multiPolygon.convexHull();
        this.convexHullUpdated = true;
        return convexHull;
    }

    public Geometry getBoundingCircle() {
        if (boundingCircleUpdated && boundingCircle !=null)
            return boundingCircle;
        boundingCircle = new MinimumBoundingCircle(getMulti()).getCircle();
        this.boundingCircleUpdated = true;
        return boundingCircle;
    }
}
