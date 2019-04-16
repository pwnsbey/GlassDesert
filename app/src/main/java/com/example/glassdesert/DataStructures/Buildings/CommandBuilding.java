package com.example.glassdesert.DataStructures.Buildings;

public class CommandBuilding extends Building {
    private int tier;
    // TODO: Replace these with strings.xml values or similar
    private String[] tierNames = {"Command Tent", "Large Command Tent"};
    private String upgradePercentage;
    private String timeRemaining;

    public CommandBuilding(int tier) {
        this.tier = tier;
        // TODO: replace these with real variables
        upgradePercentage = "22%";
        timeRemaining = "2:22:22";
    }

    @Override
    public String getName() {
        return tierNames[tier];
    }

    @Override
    public String getBuildPercentage() {
        return upgradePercentage;
    }

    @Override
    public String getTimeRemaining() { return timeRemaining; }

    @Override
    public String getUpgradingString() {
        if (timeRemaining != null) {
            return "Upgrading to " + tierNames[tier+1] + ".";
        } else {
            return "NO UPGRADING";
        }
    }
}
