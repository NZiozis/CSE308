package mm_districting;

import java.util.ArrayList;
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
	private static boolean initalized; 
	
	private State state;
	
	private Set<DemographicContext> selectedDemographics;
	private int numDistricts;
	private int numMajorityMinorityDistricts;
	
	private boolean showEachStep;
	
	//TODO: clean up and confiq arrays as needed
	private int[] blocThresholds;
	private int minorityVotingThreshold;
	private int majorityVotingThreshold;
	private int[][] demographicThresholds;
	private int[] selectedWeights;
	
	//hide constructor to force singleton access
	private AlgorithmProperties() {}

	public static AlgorithmProperties getProperties() {
		if (!initalized) {
			instance = new AlgorithmProperties();
			instance.selectedDemographics = new HashSet<>();
			//TODO: init arrays needed if decided upon
			
			initalized = true;
		}
		
		return instance;
	}

	public State getState() {
		return state;
	}

	public Set<DemographicContext> getSelectedDemographics() {
		return selectedDemographics;
	}

	public int getNumDistricts() {
		return numDistricts;
	}

	public int getNumMajorityMinorityDistricts() {
		return numMajorityMinorityDistricts;
	}

	public boolean isShowingEachStep() {
		return showEachStep;
	}


	public int getMinorityVotingThreshold() {
		return minorityVotingThreshold;
	}

	public int getMajorityVotingThreshold() {
		return majorityVotingThreshold;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setSelectedDemographics(Set<DemographicContext> selectedDemographics) {
		this.selectedDemographics = selectedDemographics;
	}

	public void setNumDistricts(int numDistricts) {
		this.numDistricts = numDistricts;
	}

	public void setNumMajorityMinorityDistricts(int numMajorityMinorityDistricts) {
		this.numMajorityMinorityDistricts = numMajorityMinorityDistricts;
	}

	public void setShowEachStep(boolean showEachStep) {
		this.showEachStep = showEachStep;
	}

	public void setMinorityVotingThreshold(int minorityVotingThreshold) {
		this.minorityVotingThreshold = minorityVotingThreshold;
	}

	public void setMajorityVotingThreshold(int majorityVotingThreshold) {
		this.majorityVotingThreshold = majorityVotingThreshold;
	}
	
}
