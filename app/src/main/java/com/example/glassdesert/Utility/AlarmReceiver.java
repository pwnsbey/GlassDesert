package com.example.glassdesert.Utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.example.glassdesert.DataStructures.Deployment;

public class AlarmReceiver extends BroadcastReceiver {
    PowerManager.WakeLock wakeLock;

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "glassdesert:backgroundwakelock");
        wakeLock.acquire();

        String action = intent.getAction();
        switch (action.charAt(0)) {
            case 'D':
                processDeployment(action.substring(1));
                break;
        }

        wakeLock.release();
    }

    private void processDeployment(String deploymentId) {
        Archivist archivist = new Archivist(ContextBoi.getContext());
        Deployment deployment = archivist.getDeployment(deploymentId);
        if (deployment.travelStatus == 2) {
            //TODO: handle deployment end case
            //TODO: remove deployment from DB
        } else {
            deployment.travelStatus += 1;
            archivist.archiveDeployment(deployment);
        }
    }
}
