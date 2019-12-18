package edu.stonybrook.politech.annealing.algorithm;

import edu.stonybrook.politech.annealing.measures.DistrictInterface;
import edu.stonybrook.politech.annealing.measures.PrecinctInterface;

import java.util.HashSet;
import java.util.Set;

public interface MeasureFunction<Precinct extends PrecinctInterface, District extends DistrictInterface<Precinct>> {
    double calculateMeasure(District district);
    default Set<MeasureFunction<Precinct, District>> subMeasures() {
        return new HashSet<>();
    }
}
