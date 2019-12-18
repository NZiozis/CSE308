package edu.stonybrook.politech.annealing;

import edu.stonybrook.politech.annealing.measures.DistrictInterface;
import edu.stonybrook.politech.annealing.measures.PrecinctInterface;

public class Move<Precinct extends PrecinctInterface, District extends DistrictInterface<Precinct>> {
    private District to;
    private District from;
    private Precinct precinct;

    public Move(District to, District from, Precinct precinct) {
        this.to = to;
        this.from = from;
        this.precinct = precinct;
    }

    public void execute() {
        from.removePrecinct(precinct);
        to.addPrecinct(precinct);
    }

    public void undo() {
        to.removePrecinct(precinct);
        from.addPrecinct(precinct);
    }

    public String toString() {
        String toID = to!=null?to.getID():"NULL";
        String fromID = from!=null?from.getID():"NULL";
        String precinctID = precinct!=null?precinct.getID():"NULL";
        return "{ "+"\"to\": \""+toID+"\", \"from\": \""+fromID+"\", \"precinct\": \""+precinctID+"\" }";
    }

    public District getTo() {
        return to;
    }

    public District getFrom() {
        return from;
    }

    public Precinct getPrecinct() {
        return precinct;
    }
}