package algorithm_steps;

import mm_districting.Edge;

public class DummyEdge extends Edge {

    public DummyEdge() {
        super(null, null);
    }

    @Override
    public double getJoinability() {
        return -1;
    }

    @Override
    public double getMajMinJoinability() {
        return -1;
    }
}
