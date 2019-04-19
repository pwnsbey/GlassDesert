package com.example.glassdesert.DataStructures;

public class Location {
    public String name;
    public int distance;
    public boolean isDiscovered = false;

    public Location(String name, int distance) {
        this.name = name;
        this.distance = distance;
    }

    public Location(String name, int distance, boolean isDiscovered) {
        this.name = name;
        this.distance = distance;
        this.isDiscovered = isDiscovered;
    }

    public String getDistanceString() {
        return String.valueOf(distance) + "farmeasures away";
    }
}
