package edu.stonybrook.politech.annealing.models.concrete;

import edu.stonybrook.politech.annealing.measures.PrecinctInterface;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;

import java.util.*;

public class Precinct
        implements PrecinctInterface {
    private final String ID;
    private final Geometry geometry;
    private final String geometryJSON;
    private final String originalDistrictID;

    private final int population;
    private final int gop_vote;
    private final int dem_vote;
    private final Set<String> neighborIDs;

    public Precinct(
            String ID,
            Geometry geometry,
            String geometryJSON,
            String districtID,
            int population,
            int gop_vote,
            int dem_vote,
            Set<String> neighborIDs) {
        this.ID = ID;
        this.geometry = geometry;
        this.geometryJSON = geometryJSON;
        this.originalDistrictID = districtID;
        this.population = population;
        this.gop_vote = gop_vote;
        this.dem_vote = dem_vote;
        this.neighborIDs = neighborIDs;
    }

    @Override
    public String getID() {
        return ID;
    }

    public String getGeometryJSON() {
        return geometryJSON;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public double getPopulationDensity() {
        if (geometry !=null && geometry.getArea() != 0)
            return getPopulation() / geometry.getArea();
        return -1;
    }

    @Override
    public String getOriginalDistrictID() {
        return originalDistrictID;
    }

    @Override
    public Set<String> getNeighborIDs() {
        return neighborIDs;
    }

    @Override
    public int getPopulation() {
        return population;
    }

    @Override
    public int getGOPVote() {
        return gop_vote;
    }

    @Override
    public int getDEMVote() {
        return dem_vote;
    }
}