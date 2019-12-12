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
import results.Result;
import util.Election;
import util.Operation;
import util.Race;

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

    public void updateGUI() {

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

        return guiResult;
    }

    private String initPhase1() {

        currentAlgorithm = new Algorithm(new GenerateInitialClusters(), new GenerateInitialEdges(), new Phase1Iteration(true));

        while (!(currentAlgorithm.run())) {}

        return "";
    }

    private Algorithm initPhase2() {
        return new Algorithm();
    }
}
