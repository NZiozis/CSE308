package util;

import mm_districting.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;



public class AddData {


    public State loadData() {

        State s1 = new State("West Virginia", 0, "Guidelines", "GEOJSON");
        District d1 = new District("GEOJSON", 1231241424, 1);
        s1.addDistrict(d1);
        DemographicContext c1 = new DemographicContext(100, 50, 10, 9, 8, 8, 5);
        DemographicContext c2 = new DemographicContext(123,43,54,6,1,3,5);
        Precinct p1 = new Precinct("COUNTY","GEOID", c1, "GEOJSON");
        Precinct p2 = new Precinct("COUNTY2", "GEOID2", c2, "GEOJSON2");
        p1.addVotingData(new Voting(123, Party.DEMOCRAT, Election.CONGRESSIONAL_2016));
        p1.addVotingData(new Voting(234, Party.REPUBLICAN, Election.CONGRESSIONAL_2016));
        p1.addNeighbor(p2);
        p2.addNeighbor(p1);
        d1.addPrecinct(p1);
        d1.addPrecinct(p2);
        s1.addDistrict(d1);
        // Continue this for all states, districts and precincts as needed

        return s1;
    }

}
