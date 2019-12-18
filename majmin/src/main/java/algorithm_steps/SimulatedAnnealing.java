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
import org.javatuples.Pair;

import java.util.*;

public class SimulatedAnnealing implements AlgorithmStep {

    Set<edu.stonybrook.politech.annealing.models.concrete.Precinct> precincts;
    edu.stonybrook.politech.annealing.models.concrete.State state;
    HashMap<Measure, Double> measures;

    public SimulatedAnnealing(HashMap<Measure, Double> m) {
        precincts = initializePrecincts();
        state = new edu.stonybrook.politech.annealing.models.concrete.State(
                AlgorithmProperties.getProperties().getState().getName(), precincts);
        measures = m;
    }

    public Set<edu.stonybrook.politech.annealing.models.concrete.Precinct> initializePrecincts() {
        //First, the precincts must be stored as the Algorithm's precinct objects.
        State state = AlgorithmProperties.getProperties().getState(); //get the state
        //Set<District> districts = state.getInitialDistricts(); //get the new districts
        Set<Cluster> clusters = state.getClusters();
        Set<edu.stonybrook.politech.annealing.models.concrete.Precinct> precincts = new HashSet(); //
        for(Cluster c : clusters) { //temporarily using initial districts
            //String districtid = Long.toString(d.getGeoId());
            String clusterid = Integer.toString(c.getID());
            for(Precinct p : c.getPrecincts()) {
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
                        precinctid, geometry, geometryJSON, clusterid, population, gop_vote, dem_vote, neighborIDs);

                precincts.add(precinct);
            }
        }
        return precincts;
    }

    @Override
    public boolean run() {
        //Map<Measure, Double> hm = new HashMap<Measure, Double>();
        //hm.put(Measure.PARTISAN_FAIRNESS, .5);
        //hm.put(Measure.COMPETITIVENESS, .3);
        //hm.put(Measure.POPULATION_EQUALITY, .6);
        MyAlgorithm algorithm = new MyAlgorithm(state, DefaultMeasures.defaultMeasuresWithWeights(measures));
        //Set<Move> moves = new HashSet<>();
        for(int i = 0; i < 50; i++) {
            Move m = algorithm.getMoveFromDistrict(algorithm.getWorstDistrict());
            System.out.println("Iteration " + (i + 1) + " finished!\n");
            //moves.add(m);
        }
        return true;
    }

    @Override
    public AlgorithmStepStatus getStatus() {
        return null;
    }

    @Override
    public void pause() {}

    @Override
    public Result onCompletion() {
        Phase1Result result = new Phase1Result();
        //HashMap<edu.stonybrook.politech.annealing.models.concrete.District, String[]> map = new HashMap();
        ArrayList<Pair<Cluster, ArrayList<String>>> map = new ArrayList();
        //Currently, the Result consists of randomly-numbered districts and an array of their precinct ids.
        //This needs to be changed to clusters and an array of their precinct ids.
        //int count = 0;
        for(edu.stonybrook.politech.annealing.models.concrete.District d : state.getDistricts()) {
            String[] ids = d.getPrecinctIDs();
            ArrayList<String> idList = new ArrayList<String>(Arrays.asList(ids));
            //edu.stonybrook.politech.annealing.models.concrete.District newDistrict = new edu.stonybrook.politech.annealing.models.concrete.District(Integer.toString(count), state);
            Cluster clust = new Cluster();
            clust.setID(Integer.parseInt(d.getID())); //district id came from clusters, so must also be an int
            map.add(new Pair<>(clust, idList));
        }
        System.out.println(map.toString());
        result.setMap(map);
        return result;
    }

}
