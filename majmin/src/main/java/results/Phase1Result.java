package results;

import mm_districting.Cluster;
import results.Result;

import java.util.HashMap;
import java.util.List;

/**
 * Contains a map between {@code Cluster} objects and a String array containing the GeoID of each precinct in the cluster.
 *
 * @author Patrick Wamsley
 */
public class Phase1Result extends Result {

    private HashMap<Cluster, String[]> map;

    public HashMap<Cluster, String[]> getMap() {
        return map;
    }

    public void setMap(HashMap<Cluster, String[]> map) {
        this.map = map;
    }
}
