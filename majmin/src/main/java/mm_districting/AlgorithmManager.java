package mm_districting;

import algorithm.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.*;
import util.AddData;
import util.Operation;
import util.Result;

import java.util.HashSet;
import java.util.Set;

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

    private Algorithm currentAlgorithm;
    @Autowired
    private StateRepository repository;
    private AddData adder;


    public static void main(String[] args) {
        SpringApplication.run(AlgorithmManager.class, args);
    }


    @RequestMapping(value = "/phase0", method = RequestMethod.POST)
    public void setDemographicMajority(@RequestBody String postPayload) {
        System.out.print(postPayload);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){
        this.adder = new AddData();
        State state = adder.loadData();
        repository.save(state);
        return "Data loaded";
    }

    @RequestMapping(value = "/state", method = RequestMethod.GET)
    public String getState(){
        Long id = new Long(0);
        State state = repository.findById(id).get();
        District[] temp = new District[10];
        District d = state.getInitialDistricts().toArray(temp)[0];
        return temp.toString();

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
    public State updateStateInProperties(State.StateID stateID) {
        State state = null; //TODO: get state

        AlgorithmProperties.getProperties().setState(state);
        return state;
    }

    public void updateGUI() {

    }

    private Algorithm initPhase0() {
        return new Algorithm();
    }

    private Algorithm initPhase1() {
        return new Algorithm();
    }

    private Algorithm initPhase2() {
        return new Algorithm();
    }
}
