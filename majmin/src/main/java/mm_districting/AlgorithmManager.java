package mm_districting;

import algorithm.Algorithm;
import algorithm_steps.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.*;
import results.Phase1Result;
import results.Result;
import util.Election;
import util.Operation;
import util.Race;
import org.javatuples.Pair;


import java.util.*;

interface StateRepository extends CrudRepository<State,Long> {

}

/**
 * Acts as the link between the algorithm and user/database.
 *
 * @author Patrick Wamsley
 * @author Niko Ziozis
 */
@SpringBootApplication
@RestController
@CrossOrigin
public class AlgorithmManager {

    ObjectMapper mapper = new ObjectMapper();
    private            Algorithm       currentAlgorithm;
    @Autowired private StateRepository repository;

    private boolean phase1FirstRun = true;

    public static void main(String[] args) {
        SpringApplication.run(AlgorithmManager.class, args);
    }

    /**
     * Gets the districts for the selected state
     *
     * @return A stringified json version of the districts. This is sent to the requester.
     */
    @RequestMapping(value = "/getDistricts", method = RequestMethod.GET)
    public String getDistricts() {
        Set<District> districts = AlgorithmProperties.getProperties().getState().getInitialDistricts();

        String guiResult = "";
        try {
            guiResult = mapper.writeValueAsString(districts);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return guiResult;
    }

    /**
     * Gets the precincts for the selected state
     *
     * @return A stringified json version of the precincts. This is sent to the requester.
     */
    @RequestMapping(value = "/getPrecincts", method = RequestMethod.GET)
    public String getPrecincts() {
        Set<Precinct> precincts = AlgorithmProperties.getProperties().getState().getPrecincts();
        String guiResult = "";
        try {
            guiResult = mapper.writeValueAsString(precincts);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return guiResult;
    }

    public Result runOperation(String inputData, Operation operation) {
        return null;
    }

    private Operation parseOperation(String input) {
        return null;
    }

    /**
     * Updates the selected State in the AlgorithmProperties singleton.
     *
     * @return The State object set.
     */
    @RequestMapping(value = "/setState", method = RequestMethod.POST)
    public void updateStateInProperties(@RequestBody String stateName) {
        State.StateID selectedStateId = State.StateID.valueOf(stateName);
        State currentState = AlgorithmProperties.getProperties().getState();
        String guiString = "";
        State state = null;
        // Prevents unnecessary update of state and getting of information
        if (currentState == null || currentState.getStateId() != selectedStateId.ordinal()) {
            Optional<State> stateOptional = repository.findById((long) selectedStateId.ordinal());
            if (stateOptional.isPresent()) {
                state = stateOptional.get();
            }

            AlgorithmProperties.getProperties().setState(state);
            state.init();
        }

    }

    @RequestMapping(value = "/getState", method = RequestMethod.GET)
    public String getStateToUI() {

        System.gc();

        State state = AlgorithmProperties.getProperties().getState();
        state.getIncumbentData();

        String guiString = "";

        try {
            guiString = mapper.writeValueAsString(state);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return guiString;
    }

    @RequestMapping(value = "/utah", method = RequestMethod.GET)
    public String getUtahGeography() {
        Long utahId = (long) State.StateID.UTAH.ordinal();
        Optional<State> utahOptional = repository.findById(utahId);
        State utah = null;
        if (utahOptional.isPresent()) {
            utah = utahOptional.get();
        }
        return utah.getGeography();
    }

    @RequestMapping(value = "/westVirginia", method = RequestMethod.GET)
    public String getWestVirginiaGeography() {
        Long westVirginiaId = (long) State.StateID.WEST_VIRGINIA.ordinal();
        Optional<State> westVirginiaOptional = repository.findById(westVirginiaId);
        State westVirginia = null;
        if (westVirginiaOptional.isPresent()) {
            westVirginia = westVirginiaOptional.get();
        }

        return westVirginia.getGeography();
    }

    @RequestMapping(value = "/florida", method = RequestMethod.GET)
    public String getFlorida() {
        Long floridaId = (long) State.StateID.FLORIDA.ordinal();
        Optional<State> floridaOptional = repository.findById(floridaId);
        State florida = null;
        if (floridaOptional.isPresent()) {
            florida = floridaOptional.get();
        }

        return florida.getGeography();
    }

    @RequestMapping(value = "/setInsomnia", method = RequestMethod.POST)
    private void setInsomniaState(@RequestBody String postPayload) {
        Map<String,String> map = null;
        try {
            map = mapper.readValue(postPayload, Map.class);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        State.StateID selectedStateId = State.StateID.valueOf(map.get("name"));
        State currentState = AlgorithmProperties.getProperties().getState();
        // Prevents unnecessary update of state and getting of information
        if (currentState == null || currentState.getStateId() != selectedStateId.ordinal()) {
            Optional<State> stateOptional = repository.findById((long) selectedStateId.ordinal());
            State state = null;
            if (stateOptional.isPresent()) {
                state = stateOptional.get();
            }

            AlgorithmProperties.getProperties().setState(state);

        }
    }

    @RequestMapping(value = "/phase0", method = RequestMethod.POST)
    private String initPhase0(@RequestBody String postPayload) {
        Map<String,Object> map = null;
        try {
            map = mapper.readValue(postPayload, Map.class);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ArrayList<String> selectedDemographicsArr = (ArrayList<String>) map.get("selectedRaces");
        Set<Race> selectedDemographics = new HashSet<>();
        for (String demographic : selectedDemographicsArr) {
            selectedDemographics.add(Race.valueOf(demographic));
        }

        AlgorithmProperties.getProperties().setPopulationMajorityThreshold((Integer) map.get("majorityPercentage"));
        AlgorithmProperties.getProperties().setVotingMajorityThreshold((Integer) map.get("votingPercentage"));
        AlgorithmProperties.getProperties().setSelectedDemographics(selectedDemographics);
        AlgorithmProperties.getProperties().setSelectedElection(Election.valueOf((String) map.get("selectedElection")));

        AlgorithmProperties.getProperties().getPrecinctsWithDemBlocs().clear();
        currentAlgorithm = new Algorithm(new DetermineDemBlocs(), new DetermineVotingBlocs());

        while (!( currentAlgorithm.run() )) {}

        ArrayList<Result> results = currentAlgorithm.getResultsToSend();

        String guiResult = "";
        try {
            guiResult = mapper.writeValueAsString(results);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        for (Precinct precinct : AlgorithmProperties.getProperties().getState().getPrecincts()) {
            precinct.setDemographicBloc(null);
        }

        return guiResult;
    }

    @RequestMapping(value = "/phase1", method = RequestMethod.POST)
    private String initPhase1(@RequestBody String postPayload) {

        Map<String,Object> map = null;
        try {
            map = mapper.readValue(postPayload, Map.class);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (phase1FirstRun) {

            phase1FirstRun = false;

            AlgorithmProperties.getProperties().setRequestedNumDistricts((Integer) map.get("numberOfDistricts"));
            AlgorithmProperties.getProperties().setMinorityVotingThreshold((Integer) map.get("minorityThreshold"));
            AlgorithmProperties.getProperties().setMajorityVotingThreshold((Integer) map.get("majorityThreshold"));

            ArrayList<String> selectedDemographicsArr = (ArrayList<String>) map.get("selectedRaces");
            boolean fullRun = (Boolean)map.get("fullRun");

            Set<Race> selectedDemographics = new HashSet<>();
            for (String demographic : selectedDemographicsArr) {
                selectedDemographics.add(Race.valueOf(demographic));
            }
            AlgorithmProperties.getProperties().setSelectedDemographics(selectedDemographics);

            currentAlgorithm =
                    new Algorithm(new GenerateInitialClusters(), new GenerateInitialEdges(), new Phase1Iteration(true));

            if (fullRun) {
                while (!(currentAlgorithm.run())) {}
                phase1FirstRun = true;
            } else {
                for (int i = 0; i < 200; i++) {
                    if (currentAlgorithm.run()) {
                        phase1FirstRun = true;
                        break;
                    }
                    currentAlgorithm.getResultsToSend().add(generatePhase1Result());
                }
                //get results
            }

        } else {
            for (int i = 0; i < 200; i++) {
                if (currentAlgorithm.run()) {
                    phase1FirstRun = true;
                    break;
                }
                currentAlgorithm.getResultsToSend().add(generatePhase1Result());
            }
            //get results
        }

        Result results = currentAlgorithm.getResultsToSend().get(0);

        String guiResult = "";
        try {
            guiResult = mapper.writeValueAsString(results);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return guiResult;
    }

    @RequestMapping(value = "/phase2", method = RequestMethod.POST)
    private String initPhase2(@RequestBody String postPayload) {
        Map<String,Object> map = null;
        try {
            map = mapper.readValue(postPayload, Map.class);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Result generatePhase1Result() {
        State state = AlgorithmProperties.getProperties().getState();
        Phase1Result result = new Phase1Result();
        ArrayList<Pair<Cluster, ArrayList<String>>> map = new ArrayList<>();

        for (Cluster c : state.getClusters()) {
            Cluster clone = new Cluster();
            clone.setDemographicContext(c.getDemographicContext());
            clone.setVotingData(c.getVotingData());
            ArrayList<String> geoIds = new ArrayList<>();
            for (Precinct p : c.getPrecincts()) {
                geoIds.add(p.getGeoId());
            }
            map.add(new Pair<>(clone, geoIds));
        }

        result.setMap(map);
        return result;
    }
}
