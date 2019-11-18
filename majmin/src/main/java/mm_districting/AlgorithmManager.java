package mm_districting;

import algorithm.Algorithm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import util.Operation;
import util.Result;
import util.Voting;

/**
 * Acts as the link between the algorithm and user/database.
 *
 * @author Patrick Wamsley
 * @author Niko Ziozis
 */
@SpringBootApplication
@RestController
@CrossOrigin
public class AlgorithmManager{

    private Algorithm currentAlgorithm;

    public static void main(String[] args){
        SpringApplication.run(AlgorithmManager.class, args);
    }

    @RequestMapping(method = RequestMethod.GET,
                    value = "/temp",
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Voting index() {
        Voting temp = new Voting(2);
        return temp;
    }

    public Result runOperation(String inputData, Operation operation){
        return null;
    }

	private Operation parseOperation(String input){
        return null;
    }

    /**
     * Updates the selected State in the AlgorithmProperties singleton.
     *
     * @return The State object set.
     */
    public State updateStateInProperties(State.StateID stateID){
        State state = null; //TODO: get state

        AlgorithmProperties.getProperties().setState(state);
        return state;
    }

    public void updateGUI(){

    }

    private Algorithm initPhase0(){
        return new Algorithm();
    }

    private Algorithm initPhase1(){
        return new Algorithm();
    }

    private Algorithm initPhase2(){
        return new Algorithm();
    }
}
