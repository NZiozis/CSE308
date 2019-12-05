package mm_districting;

import algorithm.Algorithm;
import algorithm_steps.DetermineDemBlocs;
import algorithm_steps.DetermineVotingBlocs;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.*;
import util.Election;
import util.Operation;
import util.Race;
import results.Result;

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

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "Data loaded";
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
        State.StateID stateId = State.StateID.valueOf(stateName);
        Optional<State> stateOptional = repository.findById((long) stateId.ordinal());
        State state = null;
        if (stateOptional.isPresent()) {
            state = stateOptional.get();
        }

        AlgorithmProperties.getProperties().setState(state);
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

        //TODO: actually implement
        AlgorithmProperties.getProperties().setSelectedElection(Election.valueOf((String) map.get("selectedElection")));

        currentAlgorithm = new Algorithm(new DetermineDemBlocs(), new DetermineVotingBlocs());

        while (!(currentAlgorithm.run())) {}

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

    private Algorithm initPhase1() {
        return new Algorithm();
    }

    private Algorithm initPhase2() {
        return new Algorithm();
    }
}
