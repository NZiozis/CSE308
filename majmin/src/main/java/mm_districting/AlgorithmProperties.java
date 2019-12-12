package mm_districting;

import util.Election;
import util.Race;

import java.util.HashSet;
import java.util.Set;

/**
 * Manages the properties algorithm, such as the active State, threshold values, and user selected values.
 *
 * @author Patrick Wamsley
 * @author Brett Weinger
 * @author Felix Rieg-Baumhauer
 * @author Niko Ziozis
 */
public class AlgorithmProperties {

    private static AlgorithmProperties instance;
    private static boolean             initialized;

    private State state;

    private Set<Race> selectedDemographics;
    private Set<Precinct> precinctsWithDemBlocs;

    private Election selectedElection;
    private int       numDistricts;
    private int       numMajorityMinorityDistricts;

    private int       requestedNumDistricts;

    private boolean showEachStep;

    //TODO: clean up and confiq arrays as needed
    private int     populationMajorityThreshold;
    private int     votingMajorityThreshold;
    private int     minorityVotingThreshold;
    private int     majorityVotingThreshold;
    private int[][] demographicThresholds;
    private int[]   selectedWeights;

    //hide constructor to force singleton access
    private AlgorithmProperties() {}

    public static AlgorithmProperties getProperties() {
        if (!initialized) {
            instance = new AlgorithmProperties();
            instance.selectedDemographics = new HashSet<>();
            instance.precinctsWithDemBlocs = new HashSet<>();
            //TODO: init arrays needed if decided upon

            initialized = true;
        }

        return instance;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Election getSelectedElection() {
        return selectedElection;
    }

    public void setSelectedElection(Election selectedElection) {
        this.selectedElection = selectedElection;
    }

    public void setPrecinctsWithDemBlocs(Set<Precinct> precincts) {
        this.precinctsWithDemBlocs = precincts;
    }

    public Set<Precinct> getPrecinctsWithDemBlocs() {
        return precinctsWithDemBlocs;
    }

    public int getVotingMajorityThreshold() {
        return votingMajorityThreshold;
    }

    public void setVotingMajorityThreshold(int votingMajorityThreshold) {
        this.votingMajorityThreshold = votingMajorityThreshold;
    }

    public int getBlocThreshold() { return populationMajorityThreshold; }

    public void setPopulationMajorityThreshold(int populationMajorityThreshold) {
        this.populationMajorityThreshold = populationMajorityThreshold;
    }

    public Set<Race> getSelectedDemographics() {
        return selectedDemographics;
    }

    public void setSelectedDemographics(Set<Race> selectedDemographics) {
        this.selectedDemographics = selectedDemographics;
    }

    public int getNumDistricts() {
        return numDistricts;
    }

    public void setNumDistricts(int numDistricts) {
        this.numDistricts = numDistricts;
    }

    public int getNumMajorityMinorityDistricts() {
        return numMajorityMinorityDistricts;
    }

    public void setNumMajorityMinorityDistricts(int numMajorityMinorityDistricts) {
        this.numMajorityMinorityDistricts = numMajorityMinorityDistricts;
    }

    public boolean isShowingEachStep() {
        return showEachStep;
    }

    public int getMinorityVotingThreshold() {
        return minorityVotingThreshold;
    }

    public void setMinorityVotingThreshold(int minorityVotingThreshold) {
        this.minorityVotingThreshold = minorityVotingThreshold;
    }

    public int getMajorityVotingThreshold() {
        return majorityVotingThreshold;
    }

    public void setMajorityVotingThreshold(int majorityVotingThreshold) {
        this.majorityVotingThreshold = majorityVotingThreshold;
    }

    public void setShowEachStep(boolean showEachStep) {
        this.showEachStep = showEachStep;
    }

    public int getRequestedNumDistricts() {
        return requestedNumDistricts;
    }

    public void setRequestedNumDistricts(int requestedNumDistricts) {
        this.requestedNumDistricts = requestedNumDistricts;
    }
}
