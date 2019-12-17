package algorithm_steps;

import algorithm.Algorithm;
import algorithm.AlgorithmStep;
import algorithm.AlgorithmStepStatus;
import edu.stonybrook.politech.annealing.measures.*;
import edu.stonybrook.politech.annealing.algorithm.*;
import mm_districting.*;
import org.locationtech.jts.geom.*;
import org.wololo.jts2geojson.GeoJSONReader;
import results.*;
import results.Result;
import edu.stonybrook.politech.annealing.*;
import util.Party;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SimulatedAnnealing implements AlgorithmStep {

    public SimulatedAnnealing() {
    }

    @Override
    public boolean run() {
        //Initialize precincts and state.
        Set<edu.stonybrook.politech.annealing.models.concrete.Precinct> precincts = initializePrecincts();
        edu.stonybrook.politech.annealing.models.concrete.State state = new edu.stonybrook.politech.annealing.models.concrete.State(
                AlgorithmProperties.getProperties().getState().getName(), precincts);
        Map<Measure, Double> hm = new HashMap<Measure, Double>();
        hm.put(Measure.PARTISAN_FAIRNESS, .5);
        hm.put(Measure.COMPETITIVENESS, .3);
        hm.put(Measure.POPULATION_EQUALITY, .6);
        MyAlgorithm algorithm = new MyAlgorithm(state, DefaultMeasures.defaultMeasuresWithWeights(hm));
        //The constructor should handle the algorithm being called, so return true.
        return true;
    }

    public Set<edu.stonybrook.politech.annealing.models.concrete.Precinct> initializePrecincts() {
        //First, the precincts must be stored as the Algorithm's precinct objects.
        State state = AlgorithmProperties.getProperties().getState(); //get the state
        Set<District> districts = state.getInitialDistricts(); //get the new districts
        Set<edu.stonybrook.politech.annealing.models.concrete.Precinct> precincts = new HashSet(); //
        for(District d : districts) {
            String districtid = Long.toString(d.getGeoId());
            for(Precinct p : d.getPrecincts()) {
                String precinctid = p.getGeoId();
                String geometryJSON = p.getGeography();
                GeoJSONReader reader = new GeoJSONReader();
                Geometry geometry = reader.read(geometryJSON);
                int population = p.getDemographics().getTotal();
                int gop_vote = 0;
                int dem_vote = 0;
                for (Voting v : p.getVotingSet()) {
                    if (v.getParty() == Party.DEMOCRAT) {
                        dem_vote = v.getVotes();
                    } else {
                        gop_vote = v.getVotes();
                    }
                }
                Set<String> neighborIDs = new HashSet<String>();
                for (Precinct neigh : p.getNeighbor()) {
                    neighborIDs.add(neigh.getGeoId());
                }
                edu.stonybrook.politech.annealing.models.concrete.Precinct precinct = new edu.stonybrook.politech.annealing.models.concrete.Precinct(
                        precinctid, geometry, geometryJSON, districtid, population, gop_vote, dem_vote, neighborIDs);

                precincts.add(precinct);
            }
        }
        return precincts;
    }

    @Override
    public AlgorithmStepStatus getStatus() {
        return null;
    }

    @Override
    public void pause() {}

    @Override
    public Result onCompletion() {
        return new DummyResult();
    }

}
