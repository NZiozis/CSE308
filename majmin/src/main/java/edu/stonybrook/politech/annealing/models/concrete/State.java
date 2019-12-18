package edu.stonybrook.politech.annealing.models.concrete;


import edu.stonybrook.politech.annealing.measures.StateInterface;
import java.util.*;

public class State
        implements StateInterface<Precinct, District> {

    private String name;//name is saved for later storage into the database

    private HashMap<String, District> districts;
    private HashMap<String, Precinct> precincts;

    private final int population;

    public State(String name, Set<Precinct> inPrecincts) {
        this.name = name;
        this.districts = new HashMap<>();
        this.precincts = new HashMap<>();
        for (Precinct p : inPrecincts) {
            String districtID = p.getOriginalDistrictID();
            District d = districts.get(districtID);
            if (d == null) {
                d = new District(districtID, this);
                districts.put(districtID, d);
            }
            d.addPrecinct(p);
            this.precincts.put(p.getID(), p);
        }
        this.population = districts.values().stream().mapToInt(District::getPopulation).sum();
    }

    public Set<Precinct> getPrecincts() {
        return new HashSet<>(precincts.values());
    }

    public Set<District> getDistricts() {
        return new HashSet<>(districts.values());
    }

    public District getDistrict(String distID) {
        return districts.get(distID);
    }

    public Precinct getPrecinct(String precID) {
        return precincts.get(precID);
    }

    @Override
    public int getPopulation() {
        return population;
    }
}