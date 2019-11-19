package mm_districting;

import util.Race;

/**
 * Holds demographic data in a given geographic boundry such as a state, district, or precinct.
 * 
 * @author Patrick Wamsley
 * @author Brett Weinger
 */
public class DemographicContext {
	
	private static final int NO_DEMOGRAPHIC_DATA = -1;
	
	/**
	 * The Race which votes as a bloc in this context, which can be null if no such bloc exists.
	 */
	private Race raceOfBloc;
	
	/**
	 * The total population in this context
	 */
	private int totalPopulation;
	
	/**
	 * Array containing the breakdown of the population by race.
	 */
	private int[] demographicPopulations;

	/* This is required for jackson */
	public DemographicContext(){}

	public DemographicContext(int totalPopulation) {
		this.totalPopulation = totalPopulation;
		demographicPopulations = new int[Race.values().length];
		
		for (int i = 0; i < demographicPopulations.length; i++) {
			demographicPopulations[i] = NO_DEMOGRAPHIC_DATA;
		}
	}

	public DemographicContext(Race raceOfBloc, int totalPopulation){
		this.totalPopulation = totalPopulation;
		this.raceOfBloc = raceOfBloc;
		demographicPopulations = new int[Race.values().length];

		for (int i = 0; i < demographicPopulations.length; i++) {
			demographicPopulations[i] = NO_DEMOGRAPHIC_DATA;
		}
	}
	
	public void setDemographicPopulation(Race demographic, int population) {
		demographicPopulations[demographic.ordinal()] = population;
	}
	
	public int getPopulationOfDemographic(Race demographic) {
		return demographicPopulations[demographic.ordinal()];
	}

	public Race getRaceOfBloc() {
		return raceOfBloc;
	}

	public int getTotalPopulation() {
		return totalPopulation;
	}

	public void setRaceOfBloc(Race raceOfBloc) {
		this.raceOfBloc = raceOfBloc;
	}

}
