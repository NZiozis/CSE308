package results;

import edu.stonybrook.politech.annealing.models.concrete.District;
import mm_districting.Cluster;
import results.Result;

import java.util.HashMap;
import java.util.List;

/**
 * Contains a map between {@code District} objects and a String array containing the GeoID of each precinct in the district.
 *
 * @author Patrick Wamsley
 * @author Brett Weinger
 */
public class Phase2Result extends Result {

    private HashMap<Cluster, String[]> map;

    public HashMap<Cluster, String[]> getMap() {
        return map;
    }

    public void setMap(HashMap<Cluster, String[]> map) {
        this.map = map;
    }

    public String toString() {
        return map.toString();
    }
}
