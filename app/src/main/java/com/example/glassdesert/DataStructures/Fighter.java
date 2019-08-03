package com.example.glassdesert.DataStructures;

import com.example.glassdesert.Utility.Archivist;
import com.example.glassdesert.Utility.ContextBoi;

import java.util.concurrent.TimeUnit;

public class Fighter {
    public String name;
    public String gender;
    public String race;

    public Fighter (String name,
                    String gender,
                    String race) {
        this.name = name;
        this.gender = gender;
        this.race = race;
    }

    public Deployment getDeployment() {
        Archivist archivist = new Archivist(ContextBoi.getContext());
        return archivist.getDeployment(this);
    }

    public String getStatusString() {
        Deployment deployment = getDeployment();
        if (deployment != null)
            return deployment.getDeploymentString();
        else
            return "Awaiting orders.";
    }

    // returns a "-" if no timer is applicable, otherwise returns the time remaining
    public String getTimeRemaining() {
        Deployment deployment = getDeployment();
        if (deployment != null) {
            long msRemaining = deployment.endTime - System.currentTimeMillis();
            return String.format("%d hours, %d minutes, %d seconds",
                                 TimeUnit.MILLISECONDS.toHours(msRemaining),
                                 TimeUnit.MILLISECONDS.toMinutes(msRemaining),
                                 TimeUnit.MILLISECONDS.toSeconds(msRemaining));
        }
        else
            return "-";
    }
}
