package results;

import mm_districting.Cluster;
import org.javatuples.Pair;

import java.util.ArrayList;

/**
 * Contains a map between {@code Cluster} objects and a String array containing the GeoID of each precinct in the cluster.
 *
 * @author Patrick Wamsley
 */
public class Phase1Result extends Result {

    private ArrayList<Pair<Cluster,ArrayList<String>>> clusterList;

    public ArrayList<Pair<Cluster,ArrayList<String>>> getArray() {
        return clusterList;
    }

    public void setMap(ArrayList<Pair<Cluster,ArrayList<String>>> clusterList) {
        this.clusterList = clusterList;
    }

}
