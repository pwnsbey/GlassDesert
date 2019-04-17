package com.example.glassdesert.DataStructures;

public class Fighter {
    public String name;
    public String gender;
    public String race;
    public Deployment deployment = null;

    public Fighter (String name,
                    String gender,
                    String race) {
        this.name = name;
        this.gender = gender;
        this.race = race;
    }

    public Fighter (String name,
                    String gender,
                    String race,
                    Deployment deployment) {
        this.name = name;
        this.gender = gender;
        this.race = race;
        this.deployment = deployment;
    }

    public String getStatusString() {
        if (deployment != null)
            return deployment.getDeploymentString();
        else
            return "Awaiting orders.";
    }

    // returns a "-" if no timer is applicable, otherwise returns the time remaining
    public String getTimeRemaining() {
        if (deployment != null)
            return deployment.timeRemaining;
        else
            return "-";
    }
}
