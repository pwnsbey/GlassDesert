package com.example.glassdesert.DataStructures;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.glassdesert.Utility.AlarmReceiver;
import com.example.glassdesert.Utility.Archivist;
import com.example.glassdesert.Utility.ContextBoi;

import java.util.UUID;

public class Deployment {
    public String deployId;
    public Location location;
    // 0 - en route
    // 1 - at location
    // 2 - returning
    public int travelStatus;
    public long startTime;
    // required time to complete current deployment stage
    public int reqTime;
    public long endTime;
    public Fighter assignedFighter;

    public Deployment(Location location, int travelStatus, Fighter assignedFighter) {
        this.deployId = "D" + UUID.randomUUID().toString();
        this.location = location;
        this.travelStatus = travelStatus;
        this.assignedFighter = assignedFighter;
    }

    public Deployment(String deployId, Location location, int travelStatus, Fighter assignedFighter,
                      long startTime, int reqTime, long endTime) {
        this.deployId = deployId;
        this.location = location;
        this.travelStatus = travelStatus;
        this.assignedFighter = assignedFighter;
        this.startTime = startTime;
        this.reqTime = reqTime;
        this.endTime = endTime;
    }

    private int calcAlarmWindow(int reqTime) {
        // TODO: replace this with something more intelligent.
        double window = reqTime*.1;
        return (int) window;
    }

    // Initializes the alarm for a new deployment, or the next deployment stage
    // Automagically archives the deployment record
    public void stageDeployment(int reqTime) {
        this.startTime = System.currentTimeMillis();
        this.reqTime = reqTime;
        this.endTime = startTime + reqTime;
        Context context = ContextBoi.getContext();

        Archivist archivist = new Archivist(context);
        archivist.archiveDeployment(this);

        AlarmManager deployAlarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent deployAlarmIntent = new Intent(deployId, Uri.EMPTY, context, AlarmReceiver.class);
        PendingIntent pendingDeployIntent = PendingIntent.getBroadcast(context, 1, deployAlarmIntent, 0);
        deployAlarm.setWindow(AlarmManager.RTC_WAKEUP, reqTime, calcAlarmWindow(reqTime), pendingDeployIntent);
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
