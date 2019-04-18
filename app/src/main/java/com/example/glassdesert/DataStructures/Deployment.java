package com.example.glassdesert.DataStructures;

public class Deployment {
    // TODO: change to an actual Location object
    public Location location;
    // 0 - en route
    // 1 - at location
    // 2 - returning
    public int travelStatus;
    // how much time is left for whatever the current TravelStatus is
    // TODO: change this to an actual Timer object: https://stackoverflow.com/questions/14393423/how-to-make-a-countdown-timer-in-java
    public String timeRemaining;

    public Deployment(Location location, int travelStatus, String TimeRemaining) {
        this.location = location;
        this.travelStatus = travelStatus;
        this.timeRemaining = TimeRemaining;
    }

    public String getDeploymentString() {
        String deploymentString;
        // TODO: replace with strings.xml strings or something similar
        switch (travelStatus) {
            case 0:
                deploymentString = "En route to ";
                break;
            case 1:
                deploymentString = "Fighting at ";
                break;
            case 2:
                deploymentString = "Returning from ";
                break;
            default:
                deploymentString = "BAD DEPLOYMENT ID";
                break;
        }
        deploymentString += location.name += ".";
        return deploymentString;
    }
}
