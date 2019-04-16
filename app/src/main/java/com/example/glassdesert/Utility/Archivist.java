package com.example.glassdesert.Utility;

import com.example.glassdesert.DataStructures.Buildings.Building;
import com.example.glassdesert.DataStructures.Buildings.CommandBuilding;
import com.example.glassdesert.DataStructures.Deployment;
import com.example.glassdesert.DataStructures.Fighter;

import java.util.ArrayList;

// A utility class for database (Archive) access
public class Archivist {
    private ArrayList<Fighter> fighters;
    private ArrayList<Building> buildings;

    public Archivist() {
        // TODO: Replace with actual database data
        fighters = new ArrayList<>();
        fighters.add(new Fighter(0, "Locke Arran", "Male", "Eynsham",
                new Deployment("Aion", 1, "2:22:22")));
        fighters.add(new Fighter(1, "Tierras Lin", "Female", "Eynsham"));

        buildings = new ArrayList<>();
        buildings.add(new CommandBuilding(0));
    }

    public ArrayList<Fighter> getFighterCohort() {
        return fighters;
    }

    public Fighter getFighterById(int id) {
        return fighters.get(id);
    }

    public ArrayList<Building> getBuildingList() {
        return buildings;
    }
}
