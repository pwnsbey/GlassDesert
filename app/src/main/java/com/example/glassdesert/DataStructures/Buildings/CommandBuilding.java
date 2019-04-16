package com.example.glassdesert.DataStructures.Buildings;

public class CommandBuilding extends Building {
    private int tier;
    private String upgradePercentage;

    public CommandBuilding(int tier) {
        this.tier = tier;
        // TODO: replace this with a real variable
        upgradePercentage = "22%";
    }

    @Override
    String getName() {
        // TODO: Replace these with strings.xml values or similar
        switch (this.tier) {
            case 0:
                return "Command Tent";
            case 1:
                return "Large Command Tent";
            default:
                throw new ArrayIndexOutOfBoundsException();
        }
    }

    @Override
    String getBuildPercentage() {
        return upgradePercentage;
    }
}
