package edu.stonybrook.politech.annealing.models.concrete;

public class StateOutline {
    private final String name;
    private final String geometry;

    public StateOutline(String name, String geometry) {
        this.name = name;
        this.geometry = geometry;
    }

    public String getName() {
        return name;
    }

    public String getGeometry() {
        return geometry;
    }
}