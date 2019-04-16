package com.example.glassdesert.Utility;

import com.example.glassdesert.DataStructures.Buildings.Building;
import com.example.glassdesert.DataStructures.Buildings.CommandBuilding;
import com.example.glassdesert.DataStructures.Fighter;

import java.util.ArrayList;

// A utility class for database (Archive) access
public class Archivist {
    public ArrayList<Fighter> getFighterCohort() {
        // TODO: Replace with actual database data
        ArrayList<Fighter> fighters = new ArrayList<Fighter>();
        fighters.add(new Fighter("Locke Arran", 'm', "eynsham"));
        fighters.add(new Fighter("Tierras Lin", 'f', "eynsham"));
        return fighters;
    }

    public ArrayList<Building> getBuildingList() {
        // TODO: Replace with actual database data
        ArrayList<Building> buildings = new ArrayList<Building>();
        buildings.add(new CommandBuilding(0));
        return buildings;
    }
}
