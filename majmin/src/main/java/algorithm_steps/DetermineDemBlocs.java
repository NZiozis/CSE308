/**
 * Phase0, step 1: Determine Demographics Blocs (Use case #23)
 *
 * Identifies which precincts in the selected state contain a Demographic Bloc, as defined by
 * the demographics and thresholds selected by the user.
 *
 * @see mm_districting/AlgorithmProperties
 * @author Patrick Wamsley
 */
public class DetermineDemBlocs implements AlgorithmStep {

    /**
     * Iterates through the current state's precincts and indentifies which contain demographic blocs
     * @return true when completed
     */
    @Override
    public boolean run() {
        AlgorithmProperties algProps = AlgorithmProperties.getProperties();

        Set<Race> selectedDems = algProps.getSelectedDemographics();
        State state = algProps.getState();
        Set<Precinct> precincts = state.getPrecincts();

        for (Precinct precinct : precincts) {
            DemographicContext demographicData = precinct.getDemographics();
            for (Race dem : selectedDems) {
                if (algProps.getBlocThresholds()[dem.ordinal()] <= demographicData.getByRace(dem) / demographicData.getTotal()) {
                    precinct.setDemographicBloc(dem);

                    //it's only possible to have one demographic bloc per precinct since the threshold must be over 51%
                    return true;
                }
            }
        }

        return true;
    }

    /**
     * This algorithm step completes in one iteration of run()
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
    public void onCompletition() {

    }

}