package edu.stonybrook.politech.annealing.measures;


import java.util.Set;

public interface StateInterface<
        Precinct extends PrecinctInterface,
        District extends DistrictInterface<Precinct>> {
    Set<Precinct> getPrecincts();
    Set<District> getDistricts();

    Precinct getPrecinct(String precinctId);

    District getDistrict(String precinctId);

    default int getPopulation() {
        return 0;
    }
}
