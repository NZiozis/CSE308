package algorithm_steps;

import algorithm.AlgorithmStep;
import algorithm.AlgorithmStepStatus;
import mm_districting.AlgorithmProperties;
import mm_districting.Precinct;
import mm_districting.Voting;
import results.Phase0VotingResult;
import results.Result;
import util.Election;

import java.util.HashSet;
import java.util.Set;

/**
 * Phase0, step 2: Determine Voting Blocs (Use case #24)
 * <p>
 * Identifies which precincts in the selected state contain a voting Bloc, as defined by
 * the demographics and thresholds selected by the user.
 *
 * @author Patrick Wamsley
 * @see mm_districting/AlgorithmProperties
 * @see results/Phase0VotingResult
 */
public class DetermineVotingBlocs implements AlgorithmStep {

    private Set<Precinct> results = new HashSet<>();

    /**
     * Iterates through the current state's precincts which have been previously id'd to contain a bloc,
     * and identifies the subset of precincts which contain a voting bloc.
     *
     * @return true when completed
     */
    @Override
    public boolean run() {

        AlgorithmProperties algorithmProperties = AlgorithmProperties.getProperties();
        Election selectedElection = AlgorithmProperties.getProperties().getSelectedElection();

        for (Precinct precinct : algorithmProperties.getPrecinctsWithDemBlocs()) {

            Set<Voting> electionResults = precinct.getElectionResult(selectedElection);
            int totalVotes = 0;
            for (Voting voting : electionResults) {
                totalVotes += voting.getVotes();
            }

            for (Voting voting : electionResults) {
                if (algorithmProperties.getVotingMajorityThreshold() <=
                    ( voting.getVotes() * 1.0 / totalVotes ) * 100) {
                    precinct.setVotingBloc(voting.getParty());
                    Precinct dummyCopy = new Precinct();
                    dummyCopy.setCounty(precinct.getCounty());
                    dummyCopy.setGeoId(precinct.getGeoId());
                    dummyCopy.setDemographicBloc(precinct.getDemographicBloc());
                    dummyCopy.setVotingBloc(voting.getParty());
                    dummyCopy.setDemographics(precinct.getDemographics());
                    dummyCopy.setVotingSet(electionResults);
                    results.add(dummyCopy);
                }
            }
        }

        return true;
    }

    /**
     * This algorithm step runs atomically, so the progress is always 1.
     */
    @Override
    public AlgorithmStepStatus getStatus() {
        AlgorithmStepStatus status = new AlgorithmStepStatus("Determine Demographic Blocs");
        status.setProgress(1);
        return status;
    }

    /**
     * This algorithm step is atomic and therefore cannot be paused
     */
    @Override
    public void pause() {}

    @Override
    public Result onCompletion() {
        return new Phase0VotingResult(results);
    }
}
