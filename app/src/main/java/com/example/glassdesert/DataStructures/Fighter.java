package com.example.glassdesert.DataStructures;

public class Fighter {
    public String name;
    public char gender;
    public String race;
    public Deployment deployment;

    public Fighter (String name,
                    char gender,
                    String race) {
        this.name = name;
        this.gender = gender;
        this.race = race;
        // TODO: Remove and replace with non-dummy data stuff
        this.deployment = new Deployment("Aion", 1, "2:22:22");
    }
}
